$(function () {
    loadBrandInfoTable();
})

/**
 * 加载品牌数据
 */
function loadBrandInfoTable() {
    layui.use(['table', 'layer'], function () {
        let table = layui.table         // 表格
            , layer = layui.layer;      // 弹出层

        // 表格初始化
        table.render({
            elem: '#brandTable'   // 表格 ID
            , url: '../../product/brand/getAllBranInfo.json'
            , title: '数据列表'
            , page: true    // 开启分页
            , toolbar: '#brandToolBar'    // 工具栏
            , cols: [[  // 表头
                {type: 'checkbox', fixed: 'left'}
                , {field: 'brandCode', title: '编号', width: "10%", sort: true}
                , {field: 'brandName', title: '品牌名称', width: "30%", sort: true}
                , {field: 'fletter', title: '品牌首字母', width: "23%", sort: true}
                , {field: 'sortNo', title: '排序', width: "16%", sort: true}
                , {field: 'isShow', title: '显示', width: "16%", sort: true}
            ]]
        });


        // 监听商品大类工具栏
        table.on('toolbar(brandTableFilter)', function (obj) {
            let checkStatus = table.checkStatus(obj.config.id);
            let data = checkStatus.data; // 获取当前选中行
            switch (obj.event) {
                case 'showAddBrandInfoWin':
                    showAddBrandInfoWin();
                    break;
                case 'showUpdateBrandInfoWin':
                    if (data.length === 0) {
                        layer.msg('请选择一行');
                    } else if (data.length > 1) {
                        layer.msg('只能同时编辑一个');
                    } else {
                        // layer.alert('编辑 [id]：' + checkStatus.data[0].shopCode);
                        showUpdateBrandInfoWin(checkStatus.data[0]);
                    }
                    break;
                case 'deleteBrandInfo':


                    if (data.length === 0) {
                        layer.msg('请选择一行');
                    } else {
                        layer.msg('删除');
                        let brandCodes = [];
                        for (let brand of data)
                        {
                            brandCodes.push(brand.brandCode);
                        }
                        deleteBrandInfoByBrandCodes(brandCodes);
                    }
                    break;
            }
            ;
        });


    });
}

/**
 * 根据条件查询对应的品牌信息
 */
function searchBrandByCondition() {
    layui.use(['table'], function () {
        let table = layui.table;

        table.reload('brandTable', {
            where: {
                "keyWord": $('#keyWord').val()
            }
            , page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    })
}

/**
 * 显示新增品牌窗口
 */
let addBrandLayer;

function showAddBrandInfoWin() {
    layui.use(['layer', 'transfer'], function () {
        let layer = layui.layer
            , transfer = layui.transfer;    // 弹出层

        // 同步加载所有一级分类信息
        let arr = [];
        $.ajax({
            url: "../../product/productcat/getAllBigCategoryInfoByNoPage.json",
            type: 'get',
            dataType: 'json',
            async: false, // 同步
            success: function (data) {
                let result = data.result;
                // 遍历所有仓库信息
                for (let row of result) {
                    let obj = {"value": row.catCode, "title": row.catName};
                    arr.push(obj);
                }
            }
        });


        addBrandLayer = layer.open({
            type: 1     // 基本层类型
            , title: '新增大类信息'   // 标题
            , content: $('#addBrandInfoWin')  // 内容
            , area: ['auto', 'auto']     // 宽高
            , closeBtn: 1   // 关闭按钮
            , shade: 0.3    // 遮罩
            , id: 'LAY_layuipro'    // 唯一 Id 标识
            , resize: true     // 不允许拉伸
            , moveOut: false     // 允许拖至屏外
            , moveType: 1
            , cancel: function () { // 右上关闭操作

            }
        });

        //显示搜索框
        transfer.render({
            elem: '#test4'
            , data: arr
            , title: ['待选商品大类', '适用商品大类']
            , showSearch: true
            , height: '230'
            , id: 'selectProductCat'
        })

    });
}

/**
 * 新增品牌信息
 */
function addBrandInfo() {
    layui.use(['form', 'layer', 'util', 'transfer', 'table'], function () {
        let $ = layui.$
            , form = layui.form
            , layer = layui.layer
            , util = layui.util
            , transfer = layui.transfer
            , table = layui.table;

        //自定义验证规则
        form.verify({
            checkBrandCode: function (value) {
                let row = 0;
                $.ajax({
                    url: "../../product/brand/checkBrandCode.json?brandCode=" + value,
                    type: 'get',
                    dataType: 'json',
                    async: false, // 同步
                    success: function (result) {
                        row = result.row;
                    }
                });
                if (row > 0) {
                    return "品牌编号已存在";
                }
            }
        });


        //监听提交
        form.on('submit(addBrandInfoBtn)', function (data) {


            let getData = transfer.getData('selectProductCat'); //获取右侧数据

            let bigCategoryCodes = [];

            // 获取仓库的 Code
            for (let cat of getData) {
                bigCategoryCodes.push(cat.value)
            }

            let obj = data.field;

            obj["bigCategoryCodes"] = bigCategoryCodes;


            // 发生 ajax 请求向后台提交数据
            $.post('../../product/brand/addBrandInfo.json', obj, function (result) {
                if (result.row > 0) {
                    // 关闭窗口
                    layer.close(addBrandLayer);
                    addBrandLayer = "";
                    // 情况表单
                    $("#addBrandInfoForm")[0].reset();
                    // 重新加载 table
                    table.reload('brandTable', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    // 弹出提示信息
                    layer.alert("品牌信息添加成功!!!")
                }
            }, 'json');

            return false;
        });

    });
}

/**
 * 显示更新面板
 */
let updateBrandLayer;

function showUpdateBrandInfoWin(data) {
    layui.use(['layer', 'transfer', 'form'], function () {
        let layer = layui.layer
            , transfer = layui.transfer
            , form = layui.form;    // 弹出层


        $('#test5').html("");

        form.val("updateBrandInfoFilter", data)

        // 同步加载所有一级分类信息
        let arr = [];
        $.ajax({
            url: "../../product/productcat/getAllBigCategoryInfoByNoPage.json",
            type: 'get',
            dataType: 'json',
            async: false, // 同步
            success: function (data) {
                let result = data.result;
                // 遍历所有仓库信息
                for (let row of result) {
                    let obj = {"value": row.catCode, "title": row.catName};
                    arr.push(obj);
                }
            }
        });

        let values = [];
        $.ajax({
            url: "../../product/brand/getAllCatCodeByBrandCode.json?brandCode=" + data.brandCode,
            type: 'get',
            dataType: 'json',
            async: false, // 同步
            success: function (data) {
                let result = data.result;
                // 遍历所有仓库信息
                for (let value of result) {
                    values.push(value);
                }
            }
        });


        updateBrandLayer = layer.open({
            type: 1     // 基本层类型
            , title: '新增大类信息'   // 标题
            , content: $('#updateBrandInfoWin')  // 内容
            , area: ['auto', 'auto']     // 宽高
            , closeBtn: 1   // 关闭按钮
            , shade: 0.3    // 遮罩
            , id: 'LAY_layuipro'    // 唯一 Id 标识
            , resize: true     // 不允许拉伸
            , moveOut: false     // 允许拖至屏外
            , moveType: 1
            , cancel: function () { // 右上关闭操作
            }
        });

        //显示搜索框
        transfer.render({
            elem: '#test5'
            , data: arr
            , value: values
            , title: ['待选商品大类', '适用商品大类']
            , showSearch: true
            , height: '230'
            , id: 'updateProductCat'
        })
    })
}

/**
 * 更新品牌信息
 */
function updateBrandInfo() {
    layui.use(['form', 'layer', 'util', 'transfer', 'table'], function () {
        let layer = layui.layer;
        let table = layui.table;
        let form = layui.form;
        let transfer = layui.transfer;


        //监听提交
        form.on('submit(updateBrandInfoBtn)', function (data) {
            console.log("1========")

            transfer.render();
            let getData = transfer.getData('updateProductCat'); //获取右侧数据
            console.log("2========")
            let bigCategoryCodes = [];
            console.log("3========")
            // 获取仓库的 Code
            for (let cat of getData) {
                bigCategoryCodes.push(cat.value)
            }
            console.log("4========")
            let obj = data.field;
            console.log("5========")
            obj["bigCategoryCodes"] = bigCategoryCodes;


            console.log("6========")

            // 发生 ajax 请求向后台提交数据
            $.post('../../product/brand/updateBrandInfo.json', obj, function (result) {
                if (result.row > 0) {
                    // 关闭窗口
                    layer.close(updateBrandLayer);
                    updateBrandLayer = "";
                    // 情况表单
                    $("#updateBrandInfoForm")[0].reset();
                    // 重新加载 table
                    table.reload('brandTable', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    // 弹出提示信息
                    layer.alert("品牌信息更新成功!!!")
                }
            }, 'json');

            return false;
        });
    });
}


function deleteBrandInfoByBrandCodes(brandCodes)
{
    layui.use(['table','layer'],function () {
        let table = layui.table;
        let layer = layui.layer;
        $.post('../../product/brand/deleteBrandInfoByBrandCodes.json',{"brandCodes":brandCodes},function (data) {
            if (data.row > 0) {
                table.reload('brandTable', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
                layer.alert("成功删除门店信息"+data.row+"条数据!!!")
            }
        },'json');
    });
}
$(function () {
    loadShopTable();
});

/**
 * 加载数据表格
 */
function loadShopTable() {
    layui.use(['laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element', 'slider', 'form'], function () {
        var laydate = layui.laydate //日期
            , laypage = layui.laypage //分页
            , layer = layui.layer //弹层
            , table = layui.table //表格
            , carousel = layui.carousel //轮播
            , upload = layui.upload //上传
            , element = layui.element //元素操作
            , slider = layui.slider //滑块
            , form = layui.form     //表单

        //执行一个 table 实例
        table.render({
            elem: '#shopTable'
            , url: '../../base/shop/getAllShopInfo.json' //数据接口
            , title: '用户表'
            , page: true //开启分页
            , toolbar: '#toolbarDemo' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档]
            , cols: [[ //表头
                {type: 'checkbox', fixed: 'left'}
                , {field: 'shopCode', title: '序号', width: "10%", sort: true, fixed: 'left'}
                , {field: 'shopName', title: '门店名称', width: "12%", sort: true}
                , {field: 'contact', title: '联系人', width: "12%", sort: true}
                , {field: 'phoneNo', title: '手机号', width: "12%", sort: true}
                , {field: 'address', title: '地址', width: "20%", sort: true}
                , {field: 'wareHouses', title: '管辖仓库', width: "20%", sort: true}
                , {fixed: 'right', title: '状态', width: "10%", align: 'center', toolbar: '#shopStatBar'}
            ]]
        });

        //监听头工具栏事件
        table.on('toolbar(shopTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id)
                , data = checkStatus.data; //获取选中的数据
            switch (obj.event) {
                // 新增书籍
                case 'showAddShopWin':
                    showAddShopWin();
                    break;
                // 修改数据    
                case 'showUpdateShopWin':
                    if (data.length === 0) {
                        layer.msg('请选择一行');
                    } else if (data.length > 1) {
                        layer.msg('只能同时编辑一个');
                    } else {
                        // layer.alert('编辑 [id]：' + checkStatus.data[0].shopCode);
                        showUpdateShopWin(checkStatus.data[0]);
                    }
                    break;
                // 删除数据    
                case 'deleteShopBy':
                    if (data.length === 0) {
                        layer.msg('请选择一行');
                    } else {
                        layer.msg('删除');
                        let shopCodes = [];
                        for (let shop of data)
                        {
                            shopCodes.push(shop.shopCode);
                        }
                        deleteShopInfoByShopCodes(shopCodes);
                    }
                    break;
            }
            ;
        });


        // 监听状态栏的切换
        form.on('switch(shopStatDemo)', function (data) {
            let swithcValue = data.elem.checked ? 1 : 0;
            $.post('../../base/shop/updateShopStat.json', {
                "shopStat": swithcValue,
                "shopCode": data.value
            }, function (data) {
                if (data.row == 1) {
                    layer.msg("状态修改成功");
                } else {
                    layer.msg("状态修改失败");
                }
            });
        });


    });
}

/**
 * 条件查询数据
 */
function searchShopInfoByCondition() {
    layui.use(['table'], function () {
        let table = layui.table;
        table.reload('shopTable', {
            where: {
                "shopStat": $('#shopStat').val(),
                "keyWord": $('#keyWord').val()
            }
            , page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    })
}

/**
 * 显示新增面板
 */
let addShopLayer;

function showAddShopWin() {
    layui.use(['layer', 'table', 'form', 'transfer', 'util'], function () {
        let layer = layui.layer
            , transfer = layui.transfer
            , util = layui.util;

        // 同步加载所有仓库信息
        let arr = [];
        $.ajax({
            url: "../../base/shop/getAllWareHouse.json",
            type: 'get',
            dataType: 'json',
            async: false, // 同步
            success: function (data) {
                let result = data.result;
                // 遍历所有仓库信息
                for (let row of result) {
                    let obj = {"value": row.whCode, "title": row.whName};
                    arr.push(obj);
                }
            }
        });


        // 显示新增门店窗口
        addShopLayer = layer.open({
            type: 1
            , title: "新增门店" //不显示标题栏
            , closeBtn: 1 //关闭按钮
            , area: '500' //大小
            , shade: 0.8 //遮住背景
            , id: 'LAY_layuipro' //设定一个id，防止重复弹出
            , moveType: 1 //拖拽模式，0或者1
            , content: $("#addShopInfoWin")
        });


        //显示搜索框
        transfer.render({
            elem: '#test4'
            , data: arr
            , title: ['待选仓库', '已选仓库']
            , showSearch: true
            , height: 230
            , id: 'selectWareHouse'
        });

    });
}

/**
 * 添加门店信息
 */
function addShopInfo() {
    layui.use(['form', 'layer', 'util', 'transfer', 'table'], function () {
        let $ = layui.$
            , form = layui.form
            , layer = layui.layer
            , util = layui.util
            , transfer = layui.transfer
            , table = layui.table;

        //自定义验证规则
        form.verify({
            checkShopCode: function (value) {
                let row = 0;
                $.ajax({
                    url: "../../base/shop/getShopInfoByShopCode.json?shopCode=" + value,
                    type: 'get',
                    dataType: 'json',
                    async: false, // 同步
                    success: function (result) {
                        row = result.row;
                    }
                });
                if (row > 0) {
                    return "门店编号已存在";
                }
            }
        });


        //监听提交
        form.on('submit(addShopInfoBtn)', function (data) {


            let getData = transfer.getData('selectWareHouse'); //获取右侧数据

            let wareHouseCode = [];

            // 获取仓库的 Code
            for (let wareHouse of getData) {
                wareHouseCode.push(wareHouse.value)
            }

            let obj = data.field;
            obj["wareHouseCode"] = wareHouseCode;

            // 发生 ajax 请求向后台提交数据
            $.post('../../base/shop/addShopInfo.json', obj, function (result) {
                if (result.row > 0) {
                    // 关闭窗口
                    layer.close(addShopLayer);
                    addShopLayer = "";
                    // 情况表单
                    $("#addShopInfoForm")[0].reset();
                    // 重新加载 table
                    table.reload('shopTable', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    // 弹出提示信息
                    layer.alert("门店信息添加成功!!!")
                }
            }, 'json');

            return false;
        });

    });
}

/**
 * 修改门店信息
 */
let updateShopLayer;
function showUpdateShopWin(data)
{
    layui.use(['layer', 'table', 'form','transfer'], function () {
        let layer = layui.layer;
        let table = layui.table;
        let form = layui.form;
        let transfer = layui.transfer;

        form.val("updateShopInfoFilter",data)

        let arr = [];
        // 获取所有仓库配置信息
        $.ajax({
            url: "../../base/shop/getAllWareHouse.json",
            type: 'get',
            dataType: 'json',
            async: false, // 同步
            success: function (data) {
                let result = data.result;
                // 遍历所有仓库信息
                for (let row of result) {
                    let obj = {"value": row.whCode, "title": row.whName};
                    arr.push(obj);
                }
            }
        });

        // 获取当前门店的仓库信息
        let arr2 = [];
        $.ajax({
            url: "../../base/shop/getWareHouseByShopCode.json?shopCode=" + data.shopCode,
            type: 'get',
            dataType: 'json',
            async: false, // 同步
            success: function (data) {
                arr2 = data.result;
                //console.log(arr2);
            }
        });

        // 显示新增门店窗口
        updateShopLayer = layer.open({
            type: 1
            , title: "新增门店" //不显示标题栏
            , closeBtn: 1 //关闭按钮
            , area: '500' //大小
            , shade: 0.8 //遮住背景
            , id: 'LAY_layuipro' //设定一个id，防止重复弹出
            , moveType: 1 //拖拽模式，0或者1
            , content: $("#updateShopInfoWin")
        });



        //显示搜索框
        transfer.render({
            elem: '#test5'
            , data: arr
            , value: arr2
            , title: ['待选仓库', '已选仓库']
            , showSearch: true
            , height: 230
            , id: 'selectUpdateWareHouse'
        });
    });
}

/**
 * 更新门店信息
 */
function updateShopInfo() {
    layui.use(['form', 'layer', 'util', 'transfer', 'table'], function () {
        let layer = layui.layer;
        let table = layui.table;
        let form = layui.form;
        let transfer = layui.transfer;

        //监听提交
        form.on('submit(updateShopInfoBtn)', function (data) {

            let getData = transfer.getData('selectUpdateWareHouse'); //获取右侧数据

            let wareHouseCode = [];


            // 获取仓库的 Code
            for (let wareHouse of getData) {
                wareHouseCode.push(wareHouse.value)
            }
            let obj = data.field;
            obj["wareHouseCode"] = wareHouseCode;

            // 发生 ajax 请求向后台提交数据
            $.post('../../base/shop/updateShopInfo.json', obj, function (result) {
                if (result.row > 0) {
                    // 关闭窗口
                    layer.close(updateShopLayer);
                    updateShopLayer = "";
                    // 情况表单
                    $("#updateShopInfoForm")[0].reset();
                    // 重新加载 table
                    table.reload('shopTable', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    // 弹出提示信息
                    layer.alert("门店信息更新成功!!!")
                }
            }, 'json');

            return false;
        });
    });
}

/**
 * 根据门店编号删除门店信息
 * @param shopCodes
 */
function deleteShopInfoByShopCodes(shopCodes) {
    layui.use(['table','layer'],function () {
        let table = layui.table;
        let layer = layui.layer;
        $.post('../../base/shop/deleteShopInfoByShopCodes.json',{"shopCodes":shopCodes},function (data) {
            if (data.row > 0) {
                table.reload('shopTable', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
                layer.alert("成功删除门店信息"+data.row+"条数据!!!")
            }
        },'json');
    });
}
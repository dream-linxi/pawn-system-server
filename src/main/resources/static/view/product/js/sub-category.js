let smallCatCode;
let smallCatName;
$(function () {
    let params = decodeURI(location.search);
    let arr = params.split('&');
    smallCatCode = (arr[0].split('='))[1];
    smallCatName = (arr[1].split('='))[1];

    loadSubCategoryInfoTable();
});

// 加载三级分类数据
function loadSubCategoryInfoTable()
{
    layui.use(['form','layer','table'],function () {
        let form = layui.form
            , layer = layui.layer
            , table = layui.table;

        table.render({
            elem: '#subCategoryTable'   // 表格 ID
            , url: '../../product/productcat/getAllSubCategoryInfo.json?catCode=' + smallCatCode
            , title: '数据列表'
            , page: true    // 开启分页
            , toolbar: '#subCategoryToolBar'    // 工具栏
            , cols: [[  // 表头
                {type: 'checkbox', fixed: 'left'}
                , {field: 'catCode', title: '编号', width: "10%", sort: true}
                , {field: 'catName', title: '分类名称', width: "23%", sort: true}
                , {field: 'pCatCode', title: '上级分类', width: "23%", sort: true}
                , {field: 'catLvl', title: '级别', width: "10%", sort: true}
                , {field: 'unit', title: '单位', width: "10%", sort: true}
                , {field: 'sortNo', title: '排序', width: "10%", sort: true}
                , {field: 'isShow', title: '显示', width: "10%", sort: true}
            ]]
        });

        // 监听商品大类工具栏
        table.on('toolbar(subCategoryTableFilter)', function (obj) {
            let checkStatus = table.checkStatus(obj.config.id);
            let data = checkStatus.data; // 获取当前选中行
            switch (obj.event) {
                case 'showAddSubCategoryInfoWin':
                    showAddSubCategoryInfoWin();
                    break;
                case 'showUpdateSubCategoryInfoWin':
                    if (data.length === 0) {
                        layer.msg('请选择一行');
                    } else if (data.length > 1) {
                        layer.msg('只能同时编辑一个');
                    } else {
                        // layer.alert('编辑 [id]：' + checkStatus.data[0].shopCode);
                        showUpdateSubCategoryInfoWin(checkStatus.data[0]);
                    }
                    break;
                case 'deleteSubCategoryInfo':
                    layer.msg('编辑');
                    if (data.length === 0) {
                        layer.msg('请选择一行');
                    } else {
                        let catCodes = [];
                        for (let cat of data) {
                            catCodes.push(cat.catCode);
                        }
                        deleteSubCategoryInfo(catCodes);
                    }
                    break;
                case 'returnSmallCategory':
                    location.href="./small-category.html";
                    break;
            }
            ;
        });

    });
}

/**
 * 根据条件查询对应的二级类商品信息
 */
function searchSubCategoryInfoByCondition()
{
    layui.use(['table'], function () {
        let table = layui.table;

        table.reload('subCategoryTable', {
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
 * 显示二级分类新增窗口
 */
let addSubCategoryLayer;

function showAddSubCategoryInfoWin() {

    layui.use(['layer', 'form'], function () {
        let layer = layui.layer
            , form = layui.form;

        $('#smallCatName').val(smallCatName);
        $('#pCatCode').val(smallCatCode);

        addSubCategoryLayer = layer.open({
            type: 1     // 基本层类型
            , title: '新增大类信息'   // 标题
            , content: $('#addSubCategoryInfoWin')  // 内容
            , area: ['auto', '500']     // 宽高
            , closeBtn: 1   // 关闭按钮
            , shade: 0.3    // 遮罩
            , id: 'LAY_layuipro'    // 唯一 Id 标识
            , resize: true     // 不允许拉伸
            , moveOut: false     // 允许拖至屏外
            , moveType: 1
            , cancel: function () { // 右上关闭操作

            }
        });
    });
}

/**
 * 新增三级分类
 */
function addSubCategoryInfo() {
    layui.use(['form', 'layer', 'table'], function () {
        let form = layui.form
            , layer = layui.layer
            , table = layui.table;

        //自定义验证规则
        form.verify({
            checkCatCode: function (value) {
                let row = 0;
                $.ajax({
                    url: "../../product/productcat/checkCatCode.json?catCode=" + value,
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

        form.on('submit(addSubCategoryBtn)', function (data) {

            // 发生 ajax 请求向后台提交数据
            $.post('../../product/productcat/addProductCat.json', $('#addSubCategoryInfoForm').serialize(), function (result) {
                if (result.row > 0) {
                    // 关闭窗口
                    layer.close(addSubCategoryLayer);
                    addSubCategoryLayer = "";
                    // 情况表单
                    $("#addSubCategoryInfoForm")[0].reset();
                    // 重新加载 table
                    table.reload('subCategoryTable', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    // 弹出提示信息
                    layer.alert("三级分类信息添加成功!!!")
                }
            }, 'json');

            return false;

        });

    });
}

/**
 * 修改二级分类信息
 * @param data
 */
let updateSubCategoryLayer;

function showUpdateSubCategoryInfoWin(data) {
    layui.use(['form', 'layer'], function () {
        let form = layui.form
            , layer = layui.layer;

        $('#updateSmallCatName').val(smallCatName);
        $('#updatePCatCode').val(smallCatCode);


        form.val('updateSubCategoryInfoFilter', data);

        updateSubCategoryLayer = layer.open({
            type: 1     // 基本层类型
            , title: '新增大类信息'   // 标题
            , content: $('#updateSubCategoryInfoWin')  // 内容
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

    });
}

/**
 * 更新三级分类信息
 */
function updateSubCategoryInfo() {
    layui.use(['form', 'table', 'layer'], function () {

        let form = layui.form
            , table = layui.table
            , layer = layui.layer;


        form.on('submit(updateSubCategoryBtn)', function (data) {
            // 发生 ajax 请求向后台提交数据
            $.post('../../product/productcat/updateSmallCategoryInfo.json', data.field, function (result) {
                if (result.row > 0) {
                    // 关闭窗口
                    layer.close(updateSubCategoryLayer);
                    updateSubCategoryLayer = "";
                    // 情况表单
                    $("#updateSubCategoryInfoForm")[0].reset();
                    // 重新加载 table
                    table.reload('subCategoryTable', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    // 弹出提示信息
                    layer.alert("分类信息更新成功!!!")
                }
            }, 'json');

            return false;
        });
    });
}

/**
 * 根据编号删除二级分类信息
 * @param catCodes
 */
function deleteSubCategoryInfo(catCodes)
{
    layui.use(['form', 'table', 'layer'], function () {

        let form = layui.form
            , table = layui.table
            , layer = layui.layer;


        $.post('../../product/productcat/deleteSubCategoryInfo.json', {"catCodes": catCodes} , function (result) {
            if (result.row > 0) {
                // 重新加载 table
                table.reload('subCategoryTable', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
                // 弹出提示信息
                layer.alert("分类删除成功!!!")
            }
        }, 'json');

    });
}
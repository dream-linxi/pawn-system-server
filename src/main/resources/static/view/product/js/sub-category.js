$(function () {
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
            , url: '../../product/productcat/getAllSubCategoryInfo.json'
            , title: '数据列表'
            , page: true    // 开启分页
            , toolbar: '#subCategoryToolBar'    // 工具栏
            , cols: [[  // 表头
                {type: 'checkbox', fixed: 'left'}
                , {field: 'catCode', title: '编号', width: "10%", sort: true}
                , {field: 'catName', title: '分类名称', width: "18%", sort: true}
                , {field: 'pCatCode', title: '上级分类', width: "18%", sort: true}
                , {field: 'catLvl', title: '级别', width: "10%", sort: true}
                , {field: 'unit', title: '单位', width: "10%", sort: true}
                , {field: 'sortNo', title: '排序', width: "10%", sort: true}
                , {field: 'isShow', title: '显示', width: "10%", sort: true}
            ]]
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


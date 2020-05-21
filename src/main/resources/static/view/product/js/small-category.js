$(function () {
    loadSmallCateGoryInfo();
    loadSmallCategrayTable();
});

/**
 * 动态加载下拉列表
 */
function loadSmallCateGoryInfo()
{
    layui.use(['form'],function () {
        let form = layui.form;

        getAllBigCategoryInfoByNoPage($('#bigCatCode'));

        form.render('select');//需要渲染一下
    });

}

// 加载二级分类数据
function loadSmallCategrayTable()
{
    layui.use(['table', 'layer'], function () {
        let table = layui.table         // 表格
            , layer = layui.layer;      // 弹出层

        // 表格初始化
        table.render({
            elem: '#smallCategoryTable'   // 表格 ID
            , url: '../../product/productcat/getAllSmallCategoryInfo.json'
            , title: '数据列表'
            , page: true    // 开启分页
            , toolbar: '#smallCategoryToolBar'    // 工具栏
            , cols: [[  // 表头
                  {type: 'checkbox', fixed: 'left'}
                , {field: 'catCode', title: '编号', width: "10%", sort: true}
                , {field: 'catName', title: '分类名称', width: "18%", sort: true}
                , {field: 'pCatCode', title: '所属大类', width: "18%", sort: true}
                , {field: 'catLvl', title: '级别', width: "10%", sort: true}
                , {field: 'unit', title: '单位', width: "10%", sort: true}
                , {field: 'sortNo', title: '排序', width: "10%", sort: true}
                , {field: 'isShow', title: '显示', width: "10%", sort: true}
                , {fixed: 'right', width: '10%', align:'center',title:"操作" , toolbar: '#barDemo'}
            ]]
        });


        // 监听商品大类工具栏
        table.on('toolbar(smallCategoryTableFilter)', function (obj) {
            let checkStatus = table.checkStatus(obj.config.id);
            let data = checkStatus.data; // 获取当前选中行
            switch (obj.event) {
                case 'showAddSmallCategoryInfoWin':
                    showAddSmallCategoryInfoWin();
                    break;
                case 'showUpdateSmallCategoryInfoWin':
                    if (data.length === 0) {
                        layer.msg('请选择一行');
                    } else if (data.length > 1) {
                        layer.msg('只能同时编辑一个');
                    } else {
                        // layer.alert('编辑 [id]：' + checkStatus.data[0].shopCode);
                        // showUpdateSmallCategoryInfoWin(checkStatus.data[0]);
                    }
                    break;
                case 'update':
                    layer.msg('编辑');
                    break;
            }
            ;
        });


        //监听行工具事件
        table.on('tool(smallCategoryTableFilter)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            let data = obj.data //获得当前行数据
                ,layEvent = obj.event; //获得 lay-event 对应的值
            if(layEvent === 'detail'){
                layer.msg('查看操作');
                console.log(data);
            }
        });

    });
}

/**
 * 根据条件查询对应的二级类商品信息
 */
function searchSmallCategoryInfoByCondition()
{
    layui.use(['table'], function () {
        let table = layui.table;

        table.reload('smallCategoryTable', {
            where: {
                "keyWord": $('#keyWord').val(),
                "catCode": $('#bigCatCode').val()
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
let addSmallCategoryLayer;
function showAddSmallCategoryInfoWin() 
{

    layui.use(['layer','form'], function () {
        let layer = layui.layer
            , form = layui.form;

        getAllBigCategoryInfoByNoPage($('#pCatCode'));

        form.render('select');//需要渲染一下

        addSmallCategoryLayer = layer.open({
            type: 1     // 基本层类型
            , title: '新增大类信息'   // 标题
            , content: $('#addSmallCategoryInfoWin')  // 内容
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

function addSmallCategoryInfo()
{
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

        form.on('submit(addSmallCategoryBtn)', function (data) {

            // 发生 ajax 请求向后台提交数据
            $.post('../../product/productcat/addProductCat.json', $('#addSmallCategoryInfoForm').serialize(), function (result) {
                if (result.row > 0) {
                    // 关闭窗口
                    layer.close(addSmallCategoryLayer);
                    addSmallCategoryLayer = "";
                    // 情况表单
                    $("#addSmallCategoryInfoForm")[0].reset();
                    // 重新加载 table
                    table.reload('smallCategoryTable', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    // 弹出提示信息
                    layer.alert("二级分类信息添加成功!!!")
                }
            }, 'json');

            return false;

        });

    });
}


/**
 * 获取所有一级分类
 * @param element
 */
function getAllBigCategoryInfoByNoPage(element)
{
    $.ajax({
        url: "../../product/productcat/getAllBigCategoryInfoByNoPage.json",
        type: 'get',
        dataType: 'json',
        async: false, // 同步
        success: function (data) {
            let result = data.result;
            // 遍历所有仓库信息
            let htmlStr = "<option value=\"\">全部</option>";
            for (let row of result) {
                htmlStr += "<option value='"+ row.catCode +"'>"+ row.catName +"</option>";
            }
            element.html(htmlStr);
        }
    });
}
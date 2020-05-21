$(function () {
    loadWareHouseTable();
});

/**
 * 页面加载数据
 */
function loadWareHouseTable()
{
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
            elem: '#wareHouseTable'
            , url: '../../base/warehouse/getAllWareHouseInfo.json' //数据接口
            , title: '用户表'
            , page: true //开启分页
            , toolbar: '#toolbarDemo' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档]
            , cols: [[ //表头
                {type: 'checkbox', fixed: 'left'}
                , {field: 'whCode', title: '序号', width: "10%", sort: true, fixed: 'left'}
                , {field: 'whName', title: '门店名称', width: "12%", sort: true}
                , {field: 'contact', title: '联系人', width: "12%", sort: true}
                , {field: 'phoneNo', title: '手机号', width: "12%", sort: true}
                , {field: 'address', title: '地址', width: "20%", sort: true}
                , {field: 'shops', title: '管辖仓库', width: "20%", sort: true}
                , {fixed: 'right', title: '状态', width: "10%", align: 'center', toolbar: '#wareHouseStatBar'}
            ]]
        });

        //监听头工具栏事件
        table.on('toolbar(wareHouseTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id)
                , data = checkStatus.data; //获取选中的数据
            switch (obj.event) {
                // 新增仓库
                case 'showAddWareHouseWin':
                    showAddWareHouseWin();
                    break;
                // 修改数据
                case 'showUpdateWareHouseWin':
                    if (data.length === 0) {
                        layer.msg('请选择一行');
                    } else if (data.length > 1) {
                        layer.msg('只能同时编辑一个');
                    } else {
                        // layer.alert('编辑 [id]：' + checkStatus.data[0].shopCode);
                        showUpdateWareHouseWin(checkStatus.data[0]);
                    }
                    break;
                // 删除数据
                case 'deleteWareHouseByWhCodes':
                    if (data.length === 0) {
                        layer.msg('请选择一行');
                    } else {
                        layer.msg('删除');
                        let whCodes = [];
                        for (let wh of data)
                        {
                            whCodes.push(wh.whCode);
                        }
                        deleteWareHouseByWhCodes(whCodes);
                    }
                    break;
            }
            ;
        });


        //监听状态栏的切换
        form.on('switch(wareHouseStatDemo)', function (data) {
            let switchValue = data.elem.checked ? 1 : 0;
            $.post('../../base/warehouse/updateWareHouseStat.json', {
                "whStat": switchValue,
                "whCode": data.value
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
function searchWareHouseInfoByCondition() {
    layui.use(['table'], function () {
        let table = layui.table;
        table.reload('wareHouseTable', {
            where: {
                "whStat": $('#whStat').val(),
                "keyWord": $('#keyWord').val()
            }
            , page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    })
}

/**
 * 显示仓库新增面板
 */
let addWareHouseLayer;
function showAddWareHouseWin() {
    layui.use(['layer', 'table', 'form', 'transfer', 'util'], function () {
        let layer = layui.layer
            , transfer = layui.transfer
            , util = layui.util;

        // 显示新增门店窗口
        addWareHouseLayer = layer.open({
            type: 1
            , title: "新增仓库" //不显示标题栏
            , closeBtn: 1 //关闭按钮
            , area: '500' //大小
            , shade: 0.8 //遮住背景
            , id: 'LAY_layuipro' //设定一个id，防止重复弹出
            , moveType: 1 //拖拽模式，0或者1
            , content: $("#addWareHouseInfoWin")
        });

    });
}

/**
 * 新增仓库信息
 */
function addWareHouseInfo() {
    layui.use(['form', 'layer', 'util', 'transfer', 'table'], function () {
        let $ = layui.$
            , form = layui.form
            , layer = layui.layer
            , util = layui.util
            , transfer = layui.transfer
            , table = layui.table;

        //自定义验证规则
        form.verify({
            checkWhCode: function (value) {
                let row = 0;
                $.ajax({
                    url: "../../base/warehouse/getWareHouseInfoByWhCode.json?whCode=" + value,
                    type: 'get',
                    dataType: 'json',
                    async: false, // 同步
                    success: function (result) {
                        row = result.row;
                    }
                });
                if (row > 0) {
                    return "仓库编号已存在";
                }
            }
        });


        //监听提交
        form.on('submit(addWareHouseInfoBtn)', function (data) {


            // 发生 ajax 请求向后台提交数据
            $.post('../../base/warehouse/addWareHouseInfo.json', data.field, function (result) {
                if (result.row > 0) {
                    // 关闭窗口
                    layer.close(addWareHouseLayer);
                    addWareHouseLayer = "";
                    // 清空表单
                    $("#addWareHouseInfoForm")[0].reset();
                    // 重新加载 table
                    table.reload('wareHouseTable', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    // 弹出提示信息
                    layer.alert("仓库信息添加成功!!!")
                }
            }, 'json');

            return false;
        });

    });
}

/**
 * 显示修改面板
 * @param data
 */
let updateWareHouseLayer;
function showUpdateWareHouseWin(data)
{
    layui.use(['layer', 'table', 'form','transfer'], function () {
        let layer = layui.layer;
        let table = layui.table;
        let form = layui.form;
        let transfer = layui.transfer;

        form.val("updateShopInfoFilter",data)

        // 显示新增门店窗口
        updateWareHouseLayer = layer.open({
            type: 1
            , title: "新增门店" //不显示标题栏
            , closeBtn: 1 //关闭按钮
            , area: '500' //大小
            , shade: 0.8 //遮住背景
            , id: 'LAY_layuipro' //设定一个id，防止重复弹出
            , moveType: 1 //拖拽模式，0或者1
            , content: $("#updateWareHouseInfoWin")
        });

    });
}

function updateWareHouseInfo() {
    layui.use(['form', 'layer', 'util', 'transfer', 'table'], function () {
        let layer = layui.layer;
        let table = layui.table;
        let form = layui.form;
        let transfer = layui.transfer;

        //监听提交
        form.on('submit(updateWareHouseInfoBtn)', function (data) {

            // 发生 ajax 请求向后台提交数据
            $.post('../../base/warehouse/updateWareHouseInfo.json', data.field, function (result) {
                if (result.row > 0) {
                    // 关闭窗口
                    layer.close(updateWareHouseLayer);
                    updateWareHouseLayer = "";
                    // 情况表单
                    $("#updateWareHouseInfoForm")[0].reset();
                    // 重新加载 table
                    table.reload('wareHouseTable', {
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
 * 删除仓库信息
 */
function deleteWareHouseByWhCodes(whCodes) {
    layui.use(['table','layer'],function () {
        let table = layui.table;
        let layer = layui.layer;
        $.post('../../base/warehouse/deleteWareHouseByWhCodes.json',{"whCodes":whCodes},function (data) {
            if (data.row > 0) {
                table.reload('wareHouseTable', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
                layer.alert("成功删除仓库信息"+data.row+"条数据!!!")
            }
        },'json');
    });
}
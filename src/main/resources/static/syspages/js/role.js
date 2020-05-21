// 页面加载触发
$(function () {

    layui.use('laydate', function () {
        var laydate = layui.laydate;
        //常规用法
        laydate.render({
            elem: '#createStartTime'
        });

        laydate.render({
            elem: '#createEndTime'
        });
    });

    layui.use('table', function () {
        var table = layui.table;

        table.render({
            elem: '#test'
            , url: '/sys/role/getAllRole.json'
            , toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
            , defaultToolbar: ['filter', 'exports', 'print']
            , title: '用户数据表',
            limits: [8, 10, 15, 20],
            limit: 8,
            id: 'roleTest'
            , cols: [[
                { type: 'checkbox', fixed: 'left' }
                , { field: 'roleId', title: 'ID', width: '10%', fixed: 'left', unresize: true, sort: true }
                , { field: 'roleName', title: '角色名称', width: '16%', edit: 'text' }
                , { field: 'roleDesc', title: '角色描述', width: '25%', edit: 'text', sort: true }
                , { field: 'createTime', title: '创建时间', width: '25%', sort: true }
                //, { field: 'users', title: '角色用户', width: '26%', sort: true }
                , { fixed: 'right', title: '操作', toolbar: '#barDemo', width: '20%' }
            ]]
            , page: true
        });

        //头工具栏事件
        table.on('toolbar(test)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'showAddRoleWin':
                    showAddRoleWin();
                    break;
                case 'showUpdateRoleWin':
                    showUpdateRoleWin();
                    break;
                case 'deleteRole':
                    deltetUser();
                    break;
            };
        });

        //监听行工具事件
        table.on('tool(test)', function (obj) {
            var data = obj.data;
            //console.log(obj)
            if (obj.event === 'assigneeRole') {
                assigneeUser(data.roleId);
            } else if (obj.event === 'assigneeMenu') {
                assigneeMenu(data.roleId);
            }
        });
    });



});

var addRoleLayer;
function showAddRoleWin() {
    layui.use(['layer', 'table', 'form'], function () {
        var layer = layui.layer;
        addRoleLayer = addLayer = layer.open({
            type: 1
            , title: "新增角色" //不显示标题栏
            , closeBtn: 1 //关闭按钮
            , area: '500' //大小
            , shade: 0.8 //遮住背景
            , id: 'LAY_layuipro' //设定一个id，防止重复弹出
            , btnAlign: 'c' //按钮居中对齐
            , moveType: 1 //拖拽模式，0或者1
            , content: $("#addRoleDiv")
        });
    });
}

function addRole() {
    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form
            , layer = layui.layer
            , layedit = layui.layedit;

        //自定义验证规则
        form.verify({
            checkRoleId: function (value) {
                let row = 0;
                $.ajax({
                    url: "/sys/role/getRoleByRoleId.json?roleId=" + value,
                    type: 'get',
                    dataType: 'json',
                    async: false, // 同步
                    success: function (result) {
                        row = result.row;
                    }
                });
                if (row > 0) {
                    return "角色编号已存在";
                }
            }
        });

        //监听提交
        form.on('submit(addRoleBtn)', function (data) {
            $.post('/sys/role/addRole.json', data.field, function (data) {
                if (data.row > 0) {
                    layui.use(['layer', 'table'], function () {
                        var layer = layui.layer;
                        var table = layui.table;
                        layer.close(addRoleLayer);
                        addLayer = "";
                        $("#addRoleForm")[0].reset();
                        table.reload('roleTest', {
                            page: {
                                curr: 1 //重新从第 1 页开始
                            }
                        });
                        layer.alert("添加信息成功!!!")
                    });

                }
            }, 'json');
            return false;
        });
    });
}


var updateRoleLayer;
function showUpdateRoleWin() {

    layui.use(['layer', 'table', 'form'], function () {
        var layer = layui.layer;
        let table = layui.table;
        let form = layui.form;

        //获取选中的数据行
        var checkStatus = table.checkStatus("roleTest");
        var data = checkStatus.data;


        if (data.length > 1) {
            layer.msg("只能选择一条数据! ! !");
            return;
        }
        if (data.length < 1) {
            layer.msg("请选择一条数据! ! !");
            return;
        }

        //表单赋值
        form.val("roleUpdateFormTest", data[0]);

        updateRoleLayer = addLayer = layer.open({
            type: 1
            , title: "修改角色" //不显示标题栏
            , closeBtn: 1 //关闭按钮
            , area: '500' //大小
            , shade: 0.8 //遮住背景
            , id: 'LAY_layuipro' //设定一个id，防止重复弹出
            , btnAlign: 'c' //按钮居中对齐
            , moveType: 1 //拖拽模式，0或者1
            , content: $("#updateRoleDiv")
        });
    });
}

function updateRole() {
    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form
            , layer = layui.layer
            , layedit = layui.layedit;

        //监听提交
        form.on('submit(updateRoleBtn)', function (data) {
            $.post('/sys/role/updateRoleByRoleId.json', $("#updateRoleForm").serializeArray(), function (data) {
                if (data.row > 0) {
                    layui.use(['layer', 'table'], function () {
                        var layer = layui.layer;
                        var table = layui.table;
                        layer.close(updateRoleLayer);
                        addLayer = "";
                        $("#updateRoleForm")[0].reset();
                        table.reload('roleTest', {
                            page: {
                                curr: 1 //重新从第 1 页开始
                            }
                        });
                        layer.alert("修改成功!!!")
                    });
                }
            }, 'json');
            return false;
        })
    });
}

function deltetUser() {
    layui.use(['layer', 'table', 'form'], function () {
        var layer = layui.layer;
        let table = layui.table;
        let form = layui.form;

        var checkStatus = table.checkStatus("roleTest");

        var data = checkStatus.data;
        if (data.length < 1) {
            layer.msg("您还未选择删除的数据! ! !");
            return;
        }

        let arr = [];
        for (row of data) {
            arr.push(row.roleId);
        }

        $.post('/sys/role/deleteRoleByRoleId.json', { "roleIds": arr }, function (data) {
            if (data.row > 0) {
                layer.msg("删除成功! ! !");
                table.reload('roleTest', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
                layer.alert("删除信息成功!!!");
            }
        }, 'json');
    });
}

/**
 * 条件查询
 */
function searchRoleByCondition() {
    layui.use(['layer', 'table', 'form'], function () {
        let table = layui.table;
        table.reload('roleTest', {
            where: {
                "keyWord": $('#keyword').val(),
                "createStartTime": $('#createStartTime').val(),
                "createEndTime": $('#createEndTime').val(),
            }
            , page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    })
}

/**
 * 分配用户
 */
let assigneeUserLayer;
function assigneeUser(roleId) {

    console.log(roleId);

    layui.use(['transfer', 'layer', 'util', 'table'], function () {
        var $ = layui.$
            , transfer = layui.transfer
            , layer = layui.layer
            , table = layui.table
            , util = layui.util;

        assigneeUserLayer = layer.open({
            type: 1
            , title: "分配用户" //不显示标题栏
            , closeBtn: 1 //关闭按钮
            , area: '500' //大小
            , shade: 0.8 //遮住背景
            , id: 'LAY_layuipro' //设定一个id，防止重复弹出
            , btnAlign: 'c' //按钮居中对齐
            , moveType: 1 //拖拽模式，0或者1
            , content: $("#assigneeUserDiv")
        });
        let assignedUser = [];
        let unAssignedUser = [];
        $.ajax({
            url: "/sys/role/getAllAssigneeUser.json?roleId=" + roleId,
            type: 'get',
            dataType: 'json',
            async: false, // 同步
            success: function (result) {
                console.log(result);

                for (let assigned of result.assignedUser) {
                    unAssignedUser.push({ "value": assigned.userId, "title": assigned.userName });
                }

                for (let unAssigned of result.unAssignedUser) {
                    unAssignedUser.push({ "value": unAssigned.userId, "title": unAssigned.userName });
                }

                for (let assigned of result.assignedUser) {
                    assignedUser.push(assigned.userId);
                }
            }
        });

        //显示搜索框
        transfer.render({
            elem: '#test4'
            , data: unAssignedUser
            , value: assignedUser
            , title: ['未选用户', '已选用户']
            , showSearch: true
            , height: 320
            , id: 'selectUsers'

        })

        util.event('lay-demoTransferActive', {
            getData: function (othis) {
                var getData = transfer.getData('selectUsers'); //获取右侧数据

                if (getData.length < 1) {
                    layer.alert("您还未选择任何数据");
                } else {
                    //layer.alert(JSON.stringify(getData));
                    let userIds = [];
                    userIds.push(roleId);
                    for (let user of getData) {
                        userIds.push(user.value);
                    }
                    $.post('/sys/role/addUserAndRole.json', { "userIds": userIds }, function (data) {
                        if (data.row > 0) {
                            layer.close(assigneeUserLayer);
                            assigneeUserLayer = "";
                            layer.alert("成功添加 " + data.row + " 条数据!!!")
                            table.reload('roleTest', {
                                page: {
                                    curr: 1 //重新从第 1 页开始
                                }
                            });

                        }
                    }, 'json')
                }
            }
        });
    });
}

/**
 * 分配菜单
 */
let assigneeMenuLayer;
function assigneeMenu(roleId) {

    layui.use(['tree', 'util', 'layer', 'table'], function () {
        var tree = layui.tree
            , layer = layui.layer
            , util = layui.util
            , table = layui.table;

        assigneeMenuLayer = layer.open({
            type: 1
            , title: "授权页面" //不显示标题栏
            , closeBtn: 1 //关闭按钮
            , area: '500' //大小
            , shade: 0.8 //遮住背景
            , id: 'LAY_layuipro' //设定一个id，防止重复弹出
            , btnAlign: 'c' //按钮居中对齐
            , moveType: 1 //拖拽模式，0或者1
            , content: $("#assigneeMenuDiv")
        });

        let result;
        $.ajax({
            url: "/sys/menu/getMenuList.json",
            type: 'get',
            dataType: 'json',
            async: false, // 同步
            success: function (data) {
                result=data.result;
            }
        });

        let arr=[];
        for (let i = 0; i < result.length; i++) {
            let obj;
            if(!result[i].children) {
                obj = {
                    "title":result[i].menuName,
                    "id":result[i].menuId,
                    "field":result[i].menuName
                }
            } else {
                let child = getChildMenu(result[i].children);
                obj = {
                    "title":result[i].menuName,
                    "id":result[i].menuId,
                    "field":result[i].menuName,
                    "children":child
                }
            }
            arr.push(obj);
        }

        data = arr;

        //基本演示
        tree.render({
            elem: '#test12'
            , data: data
            , showCheckbox: true  //是否显示复选框
            , id: 'demoId1'
            , isJump: true //是否允许点击节点时弹出新窗口跳转
            , click: function (obj) {
                var data = obj.data;  //获取当前点击的节点数据
                layer.msg('状态：' + obj.state + '<br>节点数据：' + JSON.stringify(data));
            }
        });

        //按钮事件
        util.event('lay-demo', {
            getChecked: function (othis) {
                var checkedData = tree.getChecked('demoId1'); //获取选中节点的数据
                let arr = [];
                arr.push(roleId);
                for(let i = 0;i < checkedData.length; i ++){
                    if(!checkedData[i].children){
                        arr.push(checkedData[i].id);
                    }else {
                        arr.push(checkedData[i].id);
                        for(let j = 0;j < checkedData[i].children.length; j ++){
                            arr.push(checkedData[i].children[j].id);
                        }
                    }
                }
                console.log(arr);
                $.post("/sys/menu/addRoleOfMenu.json",{"menuIds":arr},function(data){
                    if(data.row > 0) {
                        layer.close(assigneeMenuLayer);
                        layer.alert("授权成功");
                    }
                },'json');
            }
            , setChecked: function () {
                tree.setChecked('demoId1', [12, 16]); //勾选指定节点
            }
            , reload: function () {
                //重载实例
                tree.reload('demoId1', {

                });

            }
        });
    });
}


function getChildMenu(childrenMenu){
    console.log(childrenMenu)
    let arr = [];
    for (let i = 0; i < childrenMenu.length; i++) {
          let obj = {
                "title":childrenMenu[i].menuName,
                "id":childrenMenu[i].menuId,
                "field":childrenMenu[i].menuName
            }
        
        arr.push(obj);
    }
    return arr;
}

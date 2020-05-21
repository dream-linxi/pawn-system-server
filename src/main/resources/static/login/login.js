const USER_KEY = 'user_key';
function handleLogin(){
    $.post('/login',$('#login-form').serialize(),function (data) {
        console.log(data);
        if(data.state == 1)
        {
            localStorage.setItem(USER_KEY,JSON.stringify(data.data.principal.username));
            //location.href = "/index.html?username=" + $('#uname').val();
            location.href = "/index.html";
        } else {
            alert(data.msg);
        }
    },'json');
}

function sendCode()
{
    $.get('/sendCode?phone='+$('#phoneNumber').val(),function (data) {
        console.log(data);
        if(data.phoneCode == 1)
        {
            layer.alert(data.msg);
        } else {
            layer.alert(data.msg);
        }
    },'json');
}

function phoneLogin()
{
    $.post('/phoneLogin',$('#phone-login-form').serialize(),function (data) {
        
        if(data.state == 1)
        {
            localStorage.setItem(USER_KEY,JSON.stringify(data.data.principal.username));
            //location.href = "/index.html?username=" + $('#uname').val();
            location.href = "/index.html";
        } else {
            alert(data.msg);
        }
    },'json');
}

//
function qqLogin(){
    $.get("https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=101780702&redirect_uri=http://www.pawntest.com/qqlogin&state=test",function (data) {
        if(data.state == 1)
        {
            localStorage.setItem(USER_KEY,JSON.stringify(data.data.principal.username));
            //location.href = "/index.html?username=" + $('#uname').val();
            location.href = "/index.html";
        } else {
            alert(data.msg);
        }
    },'json');
}
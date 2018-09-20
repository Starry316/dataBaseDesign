$(function() {
    FastClick.attach(document.body);
});
$(document).ready(function(){
    $(window).scroll(function (event) {
        if ($(window).scrollTop() > 1) {
            $("#header").addClass("starry-shadow");
        }
        else{
            $("#header").removeClass("starry-shadow");
        }
    });
});
$(".swiper-container").swiper({
    loop: true,
    autoplay: 3000
});

let app = new Vue({
    el: '#app',
    data: {
        type:'',
        userName:'',
        password:'',
        phone:'',
        isLogin:false,

        isShown:false,
        couponIsShown:false,

        //feedback的数据
        feedbackContent:'',
        feedbackIsShown:false,
        feedbackList:[],

        //loginModal中的errorMes
        sError0:'',
        sError1:'',
        sError2:'',
        lError0:'',
        lError1:'',

        //预定数据
        name:'',
        telephone:'',
        timeCheckin:'',
        timeCheckOut:'',

        //userinfo中的数据
        infoName:'',
        infoPhone:'',
        infoMemberCardId:'',
        memberCardId:'',
        memberCardPass:'',




        //已预定数据
        reservelist:[],
        couponDataList:[]

    },
    methods: {
        showFeedback(){
            if (!this.isLogin){
                $('#loginModal').modal();
                return;
            }
            if (!this.feedbackIsShown){
                $('#feedback').removeClass('starry-hide');
                $('#feedback').addClass('starry-show');
                $('#feedbackIcon').removeClass('glyphicon-menu-right');
                $('#feedbackIcon').addClass('glyphicon-menu-down');
                $('#feedbackForm').removeClass('starry-hide');
                $('#feedbackStatus').addClass('starry-hide');
                this.feedbackIsShown = true;
            }
            else {
                $('#feedback').removeClass('starry-show');
                $('#feedback').addClass('starry-hide');
                $('#feedbackIcon').addClass('glyphicon-menu-right');
                $('#feedbackIcon').removeClass('glyphicon-menu-down');
                this.feedbackIsShown = false;
            }
        },
        showFeedbackStatus(){
            $('#feedbackForm').addClass('starry-hide');
            $('#feedbackStatus').removeClass('starry-hide');

            this.$http.get('/feedbackStatus').then(resp =>{
                let result = resp.body;
            if (result.status === 200) {
                this.feedbackList = result.data;
            } else{
                alert(result.message);
            }
        }).
            catch(resp =>{
                alert("请求失败，请稍后重试");
        });

            // this.$http.get('/feedbackStatus').then(resp =>{
            //     let result = resp.body;
            //     if (result.status === 200) {
            //         this.feedbackList = result.data;
            //     } else{
            //         alert(result.message);
            //     }
            // }).
            // catch(resp =>{
            //     alert("请求失败，请稍后重试");
            // });
        },
        submitFeedback(){
            let data = {
                feedbackContent:this.feedbackContent
            };
            this.$http.post('/submitFeedback',data).then(resp =>{
                let result = resp.body;
            if (result.status === 200) {
                alert("提交成功！");
            } else{
                alert(result.message);
            }
        }).
            catch(resp =>{
                alert("请求失败，请稍后重试");
        });
        },
        showUserInfo(){
            if (!this.isLogin){
                $('#loginModal').modal();
                return;
            }
            $('#userInfoBody').removeClass("starry-hide");
            $('#bindBody').addClass("starry-hide");
            this.$http.get('/userInfo').then(resp =>{
                let result = resp.body;
            if (result.status === 200) {
                let data = result.data;
                this.infoName = data.infoName;
                this.infoPhone = data.infoPhone;
                this.infoMemberCardId = data.infoMemberCardId;
            } else{
                alert(result.message);
            }
        }).
            catch(resp =>{
                alert("请求失败，请稍后重试");
        });
            $('#userModal').modal();
        },
        showBind(){
            this.memberCardId='';
            this.memberCardPass='';
            $('#userInfoBody').addClass("starry-hide");
            $('#bindBody').removeClass("starry-hide");
        },
        bind(){
            let data = {
                memberCardId:this.memberCardId,
                memberCardPass:this.memberCardPass
            };
            this.$http.post('/bindMemberCard',data).then(resp =>{
                let result = resp.body;
            if (result.status === 200) {
                alert("绑定成功！");
            } else{
                alert(result.message);
            }
        }).
            catch(resp =>{
                alert("请求失败，请稍后重试");
        });
        },
        showHadReverse(){
            if (!this.isLogin){
                $('#loginModal').modal();
                return;
            }
            if (!this.isShown){
                $('#reverseList').removeClass('starry-hide');
                $('#reverseList').addClass('starry-show');
                $('#arrowIcon').removeClass('glyphicon-menu-right');
                $('#arrowIcon').addClass('glyphicon-menu-down');
                this.getReserveList();
                this.isShown = true;
            }
            else {
                $('#reverseList').removeClass('starry-show');
                $('#reverseList').addClass('starry-hide');
                $('#arrowIcon').addClass('glyphicon-menu-right');
                $('#arrowIcon').removeClass('glyphicon-menu-down');
                this.isShown = false;
            }
        },
        showCoupon(){
            if (!this.isLogin){
                $('#loginModal').modal();
                return;
            }
            if (!this.couponIsShown){
                $('#couponList').removeClass('starry-hide');
                $('#couponList').addClass('starry-show');
                $('#couponIcon').removeClass('glyphicon-menu-right');
                $('#couponIcon').addClass('glyphicon-menu-down');
                this.$http.get('/getCouponList').then(resp =>{
                    let result = resp.body;
                if (result.status === 200) {
                    this.couponDataList = result.data;
                } else{
                    alert(result.message);
                }
            }).
                catch(resp =>{
                    alert("请求失败，请稍后重试");
            });
                this.couponIsShown = true;
            }
            else {
                $('#couponList').removeClass('starry-show');
                $('#couponList').addClass('starry-hide');
                $('#couponIcon').addClass('glyphicon-menu-right');
                $('#couponIcon').removeClass('glyphicon-menu-down');
                this.couponIsShown = false;
            }

        },
        cancel(id) {
            let reqData = {
                id: id
            }
            this.$http.post('/cancelReserve', reqData).then(resp =>{
                let result = resp.body;
            if (result.status === 200) {
                this.getReserveList();
                alert('取消成功');
            } else{
                this.getReserveList();
                alert(result.message);
            }
        }).
            catch(resp =>{
                this.getReserveList();
            alert("请求失败，请稍后重试");
        });
            this.getReserveList();
        },
        getReserveList(){
            this.$http.get('/getHadReserveList')
                .then(response => {
                let result = response.body;
            if (result.status === 200) {
                this.reservelist = result.data;
            } else {
                alert(result.message);
            }
        })
        .catch(resp => {
                alert("请求失败，请稍后重试");
        });
        },
        reserve(type){
            this.initModals();
            if (!this.isLogin){
                $('#loginModal').modal();
                return;
            }
            this.type=type;
            let selectForm ='#reserveform'+type;
            $(selectForm).removeClass('starry-hide');
            $(selectForm).addClass('starry-show');
        },
        sendReserve(type){
            if (this.name.length<1){
                alert('请填写姓名！');
                return;
            }
            if (this.telephone.length!=11){
                alert('请填写正确的联系方式！');
                return;
            }
            this.getTime();
            if (this.timeCheckin.length<1){
                alert('请选择入住时间！');
                return;
            }
            if (this.timeCheckOut.length<1){
                alert('请选择退房时间！');
                return;
            }
            let data = {
                name:this.name,
                type:this.type,
                telephone:this.telephone,
                timeCheckin:this.timeCheckin,
                timeCheckOut:this.timeCheckOut
            }
            this.$http.post('/reserve', data)
                .then(response => {
                let result = response.body;
            if (result.status === 200) {
                alert('预定成功!');
                this.initModals();
            } else {
                alert(result.message);
                this.initModals();
            }
        })
        .catch(resp => {
                alert("请求失败，请稍后重试");
            this.initModals();
        });

        },
        getTime(){
            let type =this.type;
            let timeid = '#timeCheckIn'+type;
            let date = $(timeid).val();
            let year = date.split('年')[0];
            date = date.split('年')[1];
            let month = date.split('月')[0];
            date = date.split('月')[1];
            let day = date.split('日')[0];
            date = date.split('日')[1];
            date = year+'-'+month+'-'+day;
            this.timeCheckin=date;
            timeid = '#timeCheckOut'+type;
            date = $(timeid).val();
            year = date.split('年')[0];
            date = date.split('年')[1];
            month = date.split('月')[0];
            date = date.split('月')[1];
            day = date.split('日')[0];
            date = date.split('日')[1];
            date = year+'-'+month+'-'+day;
            this.timeCheckOut=date;
        },
        initModals(){
            // 数据初始化
            this.type='';
            this.password='';
            this.phone='';
            this.name='';
            this.telephone='';

            for (var i =1;i<5;i++){
                let id = '#reserveform'+i;
                $(id).removeClass('starry-show');
                $(id).addClass('starry-hide');
            }

            //页面初始化
            $('#signUpPage').removeClass('starry-show');
            $('#signUpPage').addClass('starry-hide');
            $('#loginPage').addClass('starry-show');
            $('#loginPage').removeClass('starry-hide');

            //出错信息初始化
            $('.alert-danger').removeClass('starry-show');
            $('.alert-danger').addClass('starry-hide');

            //已预定初始化
            $('#reverseList').removeClass('starry-show');
            $('#reverseList').addClass('starry-hide');
            $('#arrowIcon').addClass('glyphicon-menu-right');
            $('#arrowIcon').removeClass('glyphicon-menu-down');
            this.isShown = false;
        },
        changeToSignUp(){
            this.userName='';
            this.password='';
            this.phone='';
            $('#loginPage').removeClass('starry-show');
            $('#loginPage').addClass('starry-hide');
            $('#signUpPage').addClass('starry-show');
            $('#signUpPage').removeClass('starry-hide');
        },
        changeToLogin(){
            this.initModals();
        },
        login(){
            $('.alert-danger').removeClass('starry-show');
            $('.alert-danger').addClass('starry-hide');
            let canGo = true;
            if (!this.userName){
                canGo=false;
                this.lError0 = '请输入用户名';
                $('#lError0').removeClass('starry-hide');
                $('#lError0').addClass('starry-show');
            }
            if (!this.password){
                canGo=false;
                this.lError1 = '请输入密码';
                $('#lError1').removeClass('starry-hide');
                $('#lError1').addClass('starry-show');
            }
            if (!canGo) return;

            let data = {
                userName:this.userName,
                password:this.password
            }
            this.$http.post('/login', data)
                .then(response => {
                let result = response.body;
            if (result.status === 200) {
                this.isLogin = true;
                $('#loginModal').modal('hide');
            } else {
                this.isLogin = false;
                alert(result.message);
            }
        })
        .catch(resp => {
                this.isLogin = false;
            $('#loginModal').modal('hide');
            alert("请求失败，请稍后重试");
        });
        },
        signUp(){
            $('.alert-danger').removeClass('starry-show');
            $('.alert-danger').addClass('starry-hide');
            let canGo = true;
            if (!this.userName){
                canGo=false;
                this.sError0 = '请输入用户名';
                $('#sError0').removeClass('starry-hide');
                $('#sError0').addClass('starry-show');
            }
            if (!this.password){
                canGo=false;
                this.sError1 = '请输入密码';
                $('#sError1').removeClass('starry-hide');
                $('#sError1').addClass('starry-show');
            }
            if (!this.phone){
                canGo=false;
                this.sError2 = '请输入手机号';
                $('#sError2').removeClass('starry-hide');
                $('#sError2').addClass('starry-show');
            }
            if (this.phone&&this.phone.length!=11){
                canGo=false;
                this.sError2 = '请输入格式正确的手机号';
                $('#sError2').removeClass('starry-hide');
                $('#sError2').addClass('starry-show');
            }
            if (!canGo) return;
            let data = {
                userName:this.userName,
                password:this.password,
                phone:this.phone
            }
            this.$http.post('/signUp', data)
                .then(response => {
                let result = response.body;
            if (result.status === 200) {
                this.isLogin = true;
                $('#loginModal').modal('hide');
            } else {
                this.isLogin = false;
                alert(result.message);
            }
        })
        .catch(resp => {
                this.isLogin = false;
            $('#loginModal').modal('hide');
            alert("请求失败，请稍后重试");
        });
        }
    },
    mounted(){
        let nowDate = new Date();
        let dateStr = ""+nowDate.getFullYear()+"-"+(nowDate.getMonth()+1)+"-"+nowDate.getDate();
        for(let i =1;i<5;i++){
            $("#timeCheckIn"+i).datetimePicker({
                title: '预计入住时间',
                yearSplit: '年',
                monthSplit: '月',
                dateSplit: '日',
                times: function () {
                    return [  // 自定义的时间
                    ];
                },
                min: dateStr,
                onChange: function (picker, values, displayValues) {
                    let date = values[0]+'-'+values[1]+'-'+values[2]
                    console.log(date);
                    this.time = date;
                }
            });
            $("#timeCheckOut"+i).datetimePicker({
                title: '预计退房时间',
                yearSplit: '年',
                monthSplit: '月',
                dateSplit: '日',
                times: function () {
                    return [  // 自定义的时间
                    ];
                },
                min: dateStr,
                onChange: function (picker, values, displayValues) {
                    let date = values[0]+'-'+values[1]+'-'+values[2]
                    console.log(date);
                    this.time = date;
                }
            });
        }
    }
});
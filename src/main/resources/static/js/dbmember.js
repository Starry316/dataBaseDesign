let app = new Vue({
    el: '#main',
    data: {
        //页面控制参数
        page: 1,
        maxPage: 3,
        checked: 0,
        errorMes: '',
        reserveNum: '',

        //搜索参数
        memberCardId:'',
        memberName:'',
        memberPhone:'',
        level:'0',

        //开卡数据
        name: '',
        phone: '',
        idcardNo: '',
        password: '',
        firstMoney: '',

        id:'',
        money:'',
        list: [{},{}],
    },
    methods: {
        showCreateModal(){
            this.name= '';
            this.phone= '';
            this.idcardNo= '';
            this.password= '';
            this.firstMoney= '';
            console.log("12121212");
            $("#createModal").modal();
        },
        showRechargeModal(id){
            this.id = id;
            $("#rechargeModal").modal();
        },
        showCancelModal(id){
            this.id = id;
            $("#cancelModal").modal();
        },
        create(){
            if (this.name ==''||this.phone== ''||this.idcardNo== ''||this.password== ''){
                alert("请填写完整信息");
                return;
            }
            if (this.idcardNo.length!=18){
                alert("请填写正确格式的身份证号码");
                return;
            }
            if (this.phone.length!=11){
                alert("请填写正确格式的手机号码");
                return;
            }
            let reqData = {
                name:this.name,
                phone: this.phone,
                idcardNo : this.idcardNo,
                password: this.password,
                firstMoney:this.firstMoney
            }
            this.$http.post('/createMemberCard', reqData).then(resp =>{
                let result = resp.body;
            if (result.status === 200) {
                $("#createModal").modal();
                alert('创建成功,卡号为：'+result.data);
                this.getData();
                this.getReserveNum();
                this.getMaxPage();
            }
            else alert(result.message);
        }).
            catch(resp =>{
                alert("请求失败，请稍后重试");
        });
            this.getData();
            this.getReserveNum();
            this.getMaxPage();

        },
        cancel() {
            let reqData = {
                id: this.id,
            }
            this.$http.post('/deleteCard', reqData).then(resp =>{
                let result = resp.body;
            if (result.status === 200) {
                $("#cancelModal").modal();
                alert('注销成功');
            } else alert(result.message);
        }).
            catch(resp =>{
                alert("请求失败，请稍后重试");
        });
            this.getData();
            this.getReserveNum();
            this.getMaxPage();
        },
        recharge() {
            let reqData = {
                id: this.id,
                money:this.money
            }
            this.$http.post('/recharge', reqData).then(resp =>{
                let result = resp.body;
            if (result.status === 200) {
                $("#rechargeModal").modal();
                alert('充值成功，目前余额为:'+result.data+'元');
            } else alert(result.message);
        }).
            catch(resp =>{
                alert("请求失败，请稍后重试");
        });
            this.getData();
            this.getReserveNum();
            this.getMaxPage();
        },
        frontPage() {
            if (this.page == 1) return;
            $('#' + this.page + 'page').removeClass("active");
            this.page = this.page - 1;
            getData();
            $('#' + this.page + 'page').addClass("active");
        },
        nextPage() {
            if (this.page == this.maxPage) return;
            $('#' + this.page + 'page').removeClass("active");
            this.page = this.page + 1;
            getData();

            $('#' + this.page + 'page').addClass("active");
        },
        turnTo(page) {
            $('#' + this.page + 'page').removeClass("active");
            this.page = page;
            getData();
            $('#' + this.page + 'page').addClass("active");
        },
        getData() {
            let data = {
                page:this.page,
                level:this.level,
                memberCardId:this.memberCardId,
                memberName:this.memberName,
                memberPhone:this.memberPhone
            }
            this.$http.post("/memberDataList",data).then(resp =>{
                let result = resp.body;
            if (result.status === 200) {
                this.list = result.data;
            }
        });
            $('#' + this.page + 'page').addClass("active");
        },
        getReserveNum() {
            this.$http.get("/getReserveNum").then(resp =>{
                let result = resp.body;
            if (result.status === 200) {
                this.reserveNum = result.data;
            }
        });
        },
        getMaxPage() {
            let data = {
                page:this.page,
                level:this.level,
                memberCardId:this.memberCardId,
                memberName:this.memberName,
                memberPhone:this.memberPhone
            }
            this.$http.post("/getMemberMaxPage",data).then(resp =>{
                let result = resp.body;
            if (result.status === 200) {
                this.maxPage = result.data;
            }
        });
        }
    },
    created() {
        this.page = 1;
        this.getData();
        this.getReserveNum();
        this.getMaxPage();
    }
});

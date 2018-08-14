let app = new Vue({
    el: '#main',
    data: {
        //页面控制参数
        page: 1,
        maxPage: 3,
        checked: 0,
        errorMes: '',
        reserveNum: '',

        //目前正在操作的房间id
        selectedRoomId: '',

        list: [],
    },
    methods: {
        cancel(roomId,id) {
            alert(id);
            alert('room '+roomId);
            let reqData = {
                roomId: roomId,
                id: id
            }
            this.$http.post('/cancelReserve', reqData).then(resp =>{
                let result = resp.body;
            if (result.status === 200) {
                alert('取消成功');
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
            this.$http.get("/getDataList/" + this.page).then(resp =>{
                let result = resp.body;
            if (result.status === 200) {
                this.list = result.data;
            }
        });
            $('#' + this.page + 'page').addClass("active");
        },
        nextPage() {
            if (this.page == this.maxPage) return;
            $('#' + this.page + 'page').removeClass("active");
            this.page = this.page + 1;
            this.$http.get("/getDataList/" + this.page).then(resp =>{
                let result = resp.body;
            if (result.status === 200) {
                this.list = result.data;
            }
        });

            $('#' + this.page + 'page').addClass("active");
        },
        turnTo(page) {
            $('#' + this.page + 'page').removeClass("active");
            this.page = page;
            this.$http.get("/getDataList/" + this.page).then(resp =>{
                let result = resp.body;
            if (result.status === 200) {
                this.list = result.data;
            }
        });
            $('#' + this.page + 'page').addClass("active");
        },
        getData() {
            this.$http.get("/getReverseDataList/" + this.page).then(resp =>{
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
            this.$http.get("/getReverseMaxPage").then(resp =>{
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




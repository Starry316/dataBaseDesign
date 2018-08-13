let app = new Vue({
    el: '#main',
    data: {
        //搜索用的参数
        roomStatus: '1',
        checkInTime: '',
        checkOutTime: '',
        roomId: '',
        customerName: '',
        customerIdNo: '',

        //页面控制参数
        page: 1,
        maxPage: 3,
        checked: 0,
        errorMes: '',
        reserveNum: '',
        maxChangePage: 1,
        pageChange: 1,

        //目前正在操作的房间id
        selectedRoomId: '',

        //入住用的参数
        signName: '',
        signIdcardNo: '',
        signPhoneNum: '',
        signCheckOutTime: '',

        //续住用的参数
        delayCheckOutTime: '',

        //退房用参数
        quitCheckInTime: '',
        quitCheckOutTime: '',
        paymentPerDay: '',
        paymentTotal: '',
        discount: '',
        actualPayment: '',

        //展示预定信息用参数
        reserveRoomId: '',
        reserveName: '',
        reservePhone: '',
        reserveCheckInTime: '',
        reserveCheckOutTime: '',

        //换房的参数
        changeList: [],

        list: [],
    },
    methods: {
        reserveInfo(roomId) {
            let reqData = {
                selectedRoomId: roomId
            };
            this.$http.post('/reserveInfo', reqData).then(response =>{
                let result = response.body;
            if (result.status === 200) {
                let data = result.data;
                this.reserveRoomId = data.roomId;
                this.reserveName = data.name;
                this.reservePhone = data.phone;
                this.reserveCheckInTime = data.reserveCheckInTime;
                this.reserveCheckOutTime = data.reserveCheckOutTime;

            } else {
                alert(result.message);
            }
        }).
            catch(resp =>{
                alert("请求失败，请稍后重试");
        });
            $('#reserveInfoModal').modal();

        },
        //搜索
        search() {
            let errMes = $("#errorMes");
            errMes.removeClass('showMes');
            errMes.addClass('hide');
            this.getDate();
            if (! (this.roomStatus || this.checkInTime || this.checkOutTime || this.roomId || this.customerName || this.customerIdNo)) {
                this.errorMes = '请至少填写一个搜索信息！';
                errMes.removeClass('hide');
                errMes.addClass('showMes');
                return;
            }
            if (this.customerIdNo.length > 0 && this.customerIdNo.length != 18) {
                this.errorMes = '请输入正确的身份证号码！';
                errMes.removeClass('hide');
                errMes.addClass('showMes');
                return;
            }
            $('#' + this.page + 'page').removeClass("active");
            this.page = 1;
            this.getMaxPage();
            this.getData();
            // let reqData = {
            //     roomStatus: this.roomStatus,
            //     checkInTime: this.checkInTime,
            //     checkOutTime: this.checkOutTime,
            //     roomId: this.roomId,
            //     customerName : this.customerName,
            //     customerIdNo: this.customerIdNo
            // };
            //
            // this.$http.post('/search', reqData)
            //     .then(response => {
            //     let result = response.body;
            // if (result.status === 200) {
            //     this.list = result.data;
            // } else {
            //     alert(result.message);
            // }
            // })
            // .catch(resp => {
            //         alert("请求失败，请稍后再试");
            // });
        },

        //入住
        showCheckInModal(roomId) {
            this.selectedRoomId = roomId;
            $('#idNoCheck').removeClass('showMes');
            $('#idNoCheck').addClass('hide');
            $('#phoneCheck').removeClass('showMes');
            $('#phoneCheck').addClass('hide');
            this.signIdcardNo='';
            this.signPhoneNum='';
            this.signName='';
            this.signCheckOutTime='';
            this.selectedRoomId='';
            let reqData = {
                selectedRoomId: this.selectedRoomId
            };
            this.$http.post('/reserveInfo', reqData).then(response =>{
                let result = response.body;
            if (result.status === 200) {
                let data = result.data;
                if (data.isNull) {
                    $('#checkInModal').modal();
                    return;
                }
                this.signName = data.name;
                this.signPhoneNum = data.phone;
                this.signCheckOutTime = data.reserveCheckOutTime;

            } else {
                alert(result.message);
            }
        }).
            catch(resp =>{
                alert("请求失败，请稍后重试");
        });
            $('#checkInModal').modal();
        },
        checkIn() {
            this.signCheckOutTime = $('#signCheckOutTime').val();
            if (! (this.signIdcardNo && this.signPhoneNum && this.signName && this.signCheckOutTime)) {
                alert("请填写完整信息！");
                return;
            }
            if (this.signIdcardNo.length != 18) {
                alert("请正确的身份证号码！");
                $('#idNoCheck').removeClass('hide');
                $('#idNoCheck').addClass('showMes');
                return;
            }
            if (this.signPhoneNum.length != 11) {
                alert("请正确的手机号码！");
                $('#phoneCheck').removeClass('hide');
                $('#phoneCheck').addClass('showMes');
                return;
            }
            let reqData = {
                signIdcardNo: this.signIdcardNo,
                signPhoneNum: this.signPhoneNum,
                signName: this.signName,
                signCheckOutTime: this.signCheckOutTime,
                selectedRoomId: this.selectedRoomId
            };
            this.$http.post('/checkIn', reqData).then(response =>{
                let result = response.body;
            if (result.status === 200) {
                this.getData();
                alert("入住成功！");
            } else {
                this.getData();
                alert(result.message);
            }
        }).
            catch(resp =>{
                alert("请求失败，请稍后重试");
            $('#checkInModal').modal('hide');
        });
            $('#checkInModal').modal('hide');
        },

        //续住
        showDelayModal(roomId) {
            this.selectedRoomId = roomId;
            $('#delayModal').modal();
        },
        delay() {
            this.delayCheckOutTime = $('#delayCheckOutTime').val();
            if (!this.delayCheckOutTime) {
                alert('请选择续住时间');
                return;
            }
            let reqData = {
                selectedRoomId: this.selectedRoomId,
                delayCheckOutTime: this.delayCheckOutTime
            }
            this.$http.post('/delay', reqData).then(response =>{
                let result = response.body;
            if (result.status === 200) {
                this.getData();
                alert("续住成功！");
            } else {
                this.getData();
                alert(result.message);
            }
        }).
            catch(resp =>{
                alert("请求失败，请稍后重试");
            $('#delayModal').modal('hide');
        });
            $('#delayModal').modal('hide');
        },

        //退房
        showCheckOutModal(roomId) {
            this.selectedRoomId = roomId;
            let reqData = {
                selectedRoomId: this.selectedRoomId,
            };
            this.$http.post('/checkOutInfo', reqData).then(response =>{
                let result = response.body;
            if (result.status === 200) {
                let data = result.data;
                this.quitCheckInTime = data.quitCheckInTime;
                this.quitCheckOutTime = data.quitCheckOutTime;
                this.paymentPerDay = data.paymentPerDay;
                this.paymentTotal = data.paymentTotal;
                this.discount = data.discount;
                this.actualPayment = data.actualPayment;
            } else {
                alert(result.message);
            }
        }).
            catch(resp =>{
                alert("请求失败，请稍后重试");
        });
            $('#checkOut').modal();
        },
        checkOut() {
            let reqData = {
                selectedRoomId: this.selectedRoomId,
            }
            this.$http.post('/checkOut', reqData).then(response =>{
                let result = response.body;
            if (result.status === 200) {
                this.getData();
                alert("退房成功！");
            } else {
                this.getData();
                alert(result.message);
            }
        }).
            catch(resp =>{
                alert("请求失败，请稍后重试");
            $('#checkOut').modal('hide');
        });
            $('#checkOut').modal('hide');
        },

        //换房
        change(roomId) {
            this.selectedRoomId = roomId;
            this.initChangePage();
            let reqData = {
                selectedRoomId: this.selectedRoomId,
            };
            this.$http.post('/checkOutInfo', reqData).then(response =>{
                let result = response.body;
            if (result.status === 200) {
                let data = result.data;
                this.quitCheckInTime = data.quitCheckInTime;
                this.quitCheckOutTime = data.quitCheckOutTime;
                this.paymentPerDay = data.paymentPerDay;
                this.paymentTotal = data.paymentTotal;
                this.discount = data.discount;
                this.actualPayment = data.actualPayment;
            } else {
                alert(result.message);
            }
        }).
            catch(resp =>{
                alert("请求失败，请稍后重试");
        });
            $('#change').modal();
            this.getChangeRoomList();
            this.getChangeRoomMaxPage();
        },
        changeRoom(roomId) {
            let reqData = {
                roomId: roomId,
                selectedRoomId: this.selectedRoomId
            };
            this.$http.post('/changeRoom', reqData).then(response =>{
                let result = response.body;
            if (result.status === 200) {
                this.getData();
                alert("换房成功！");
            } else {
                this.getData();
                alert(result.message);
            }
        }).
            catch(resp =>{
                alert("请求失败，请稍后重试");
            $('#change').modal('hide');
        });
            $('#change').modal('hide');
        },
        changePage() {
            $('#changePage1').addClass('hide');
            $('#changePage2').removeClass('hide');
            $('#changePage2').addClass('showMes');

        },
        getChangeRoomList() {
            this.$http.get('/changeRoomList/' + this.pageChange).then(response =>{
                let result = response.body;
            if (result.status === 200) {
                this.changeList = result.data;
            } else {
                alert(result.message);
            }
        }).
            catch(resp =>{
                alert("请求失败，请稍后重试");
        });
        },
        getChangeRoomMaxPage() {
            this.$http.get("/getMaxChangePage").then(response =>{
                let result = response.body;
            if (result.status === 200) {
                this.maxChangePage = result.data;
            } else {
                alert(result.message);
            }
        }).
            catch(resp =>{
                alert("请求失败，请稍后重试");
        });
        },

        initChangePage() {
            $('#changePage2').addClass('hide');
            $('#changePage1').removeClass('hide');
            $('#changePage1').addClass('showMes');
            this.maxChangePage = 1;
            this.pageChange = 1;
        },
        changeFrontPage() {
            if (this.pageChange == 1) return;
            $('#' + this.pageChange + 'changePage').removeClass("active");
            this.pageChange = this.pageChange - 1;
            this.getChangeRoomList();
        },
        changeNextPage() {
            if (this.pageChange == this.maxChangePage) return;
            $('#' + this.pageChange + 'changePage').removeClass("active");
            this.pageChange = this.pageChange + 1;
            this.getChangeRoomList();
        },
        changeTurnTo(page) {
            $('#' + this.pageChange + 'changePage').removeClass("active");
            this.pageChange = page;
            this.getChangeRoomList();
        },

        frontPage() {
            if (this.page == 1) return;
            $('#' + this.page + 'page').removeClass("active");
            this.page = this.page - 1;
            this.getData();
        },
        nextPage() {
            if (this.page == this.maxPage) return;
            $('#' + this.page + 'page').removeClass("active");
            this.page = this.page + 1;
            this.getData();
        },
        turnTo(page) {
            $('#' + this.page + 'page').removeClass("active");
            this.page = page;
            this.getData();
        },
        getDate() {
            let checkInTime = $('#checkInTime');
            let checkOutTime = $('#checkOutTime');
            this.checkInTime = checkInTime.val();
            this.checkOutTime = checkOutTime.val();
        },
        getData() {
            let reqData = {
                roomStatus: this.roomStatus,
                checkInTime: this.checkInTime,
                checkOutTime: this.checkOutTime,
                roomId: this.roomId,
                customerName: this.customerName,
                customerIdNo: this.customerIdNo
            };
            this.$http.post("/dataList/" + this.page, reqData).then(resp =>{
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
            this.page = 1;
            let reqData = {
                roomStatus: this.roomStatus,
                checkInTime: this.checkInTime,
                checkOutTime: this.checkOutTime,
                roomId: this.roomId,
                customerName: this.customerName,
                customerIdNo: this.customerIdNo
            };
            this.$http.post("/getMaxPage", reqData).then(resp =>{
                let result = resp.body;
            if (result.status === 200) {
                this.maxPage = result.data;
            }
        });
        }
    },
    created() {
        this.getMaxPage();
        this.getData();
        this.getReserveNum();
    }
});
const nowTimeStamp = Date.parse(new Date());

$.fn.datetimepicker.dates['zh'] = {
    days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
    daysShort: ["日", "一", "二", "三", "四", "五", "六", "日"],
    daysMin: ["日", "一", "二", "三", "四", "五", "六", "日"],
    months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
    monthsShort: ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"],
    meridiem: ["上午", "下午"],
    today: "今天"
};

function initDatetimepicker(tagId) {
    const control = $('#' + tagId);
    control.datetimepicker({
        language: 'zh',
        //用自己设置的时间文字
        todayBtn: 1,
        //是否显示今天按钮，0为不显示
        autoclose: 1,
        //选完时间后是否自动关闭
        todayHighlight: 1,
        //高亮显示当天日期
        startView: 2,
        //0从小时视图开始，选分;1	从天视图开始，选小时;2从月视图开始，选天;3从年视图开始，选月;4从十年视图开始，选年
        minView: 2,
        //最精确时间，默认0；0从小时视图开始，选分；1从天视图开始，选小时；2从月视图开始，选天；3从年视图开始，选月；4从十年视图开始，选年
        //maxView:4,  //默认值：4, ‘decade’
        forceParse: 0,
        //强制解析,你输入的可能不正规，但是它胡强制尽量解析成你规定的格式（format）
        format: 'yyyy-mm-dd',
        // 格式,注意ii才是分，mm或MM都是月
        minuteStep: 5,
        //选择分钟时的跨度，默认为5分钟
        showMeridian: 0,
        //在日期和小时选择界面，出现上下午的选项,默认false
    });
}
initDatetimepicker('checkInTime');
initDatetimepicker('checkOutTime');
initDatetimepicker('signCheckOutTime');
initDatetimepicker('delayCheckOutTime');

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
    roomId:'',
    customerName:'',
    checkInTime:'',
    checkOutTime:'',


    customerInfoList:[],
    list: [{},{}],
  },
  methods: {
    showCustomerInfo(id){
      this.$http.get('/getCustomerInfo/'+id).then(resp =>{
        let result = resp.body;
        if (result.status === 200) {
          this.customerInfoList = result.data;
          $("#createModal").modal();
        }
        else alert(result.message);
      }).
      catch(resp =>{
        alert("请求失败，请稍后重试");
      });
      $("#customerInfoModal").modal();
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
      this.checkInTime = $('#checkInTime').val();
      this.checkOutTime = $('#checkOutTime').val();
      let data = {
        page:this.page,
        roomId:this.roomId,
        customerName:this.customerName,
        checkInTime:this.checkInTime,
        checkOutTime:this.checkOutTime
      }
      this.$http.post("/recordDataList",data).then(resp =>{
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
        roomId:this.roomId,
        customerName:this.customerName,
        checkInTime:this.checkInTime,
        checkOutTime:this.checkOutTime
      }
      this.$http.post("/getRecordMaxPage",data).then(resp =>{
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

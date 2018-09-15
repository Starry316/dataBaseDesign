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
    customerPhone:'',


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
      let data = {
        page:this.page,
        roomId:this.roomId,
        customerName:this.customerName,
        customerPhone:this.customerPhone
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
        page:this.page,
        roomId:this.roomId,
        customerName:this.customerName,
        customerPhone:this.customerPhone
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

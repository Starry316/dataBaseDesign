let app = new Vue({
  el: '#main',
  data: {
    //页面控制参数
    page: 1,
    maxPage: 3,
    reserveNum: '',

    feedbackContent:'',

    //搜索参数
    dealStatus:3,


    customerInfoList:[{name:'asda',idcardNo:'123456789012345678',phoneNum:'12345678901'},{}],
    list: [{},{}],
  },
  methods: {
    recevice(id){
      this.$http.get('/receviceFeedback/'+id).then(resp =>{
        let result = resp.body;
        if (result.status === 200) {
            this.getData();
            this.getReserveNum();
            this.getMaxPage();
          alert('处理成功!');
        }
        else alert(result.message);
      }).
      catch(resp =>{
        alert("请求失败，请稍后重试");
      });
    },
    complete(id){
      this.$http.get('/completeFeedback/'+id).then(resp =>{
        let result = resp.body;
        if (result.status === 200) {
            this.getData();
            this.getReserveNum();
            this.getMaxPage();
          alert('处理成功！');
        }
        else alert(result.message);
      }).
      catch(resp =>{
        alert("请求失败，请稍后重试");
      });
    },
    showFeedbackInfo(id){
      this.$http.get('/getFeedbackContent/'+id).then(resp =>{
        let result = resp.body;
        if (result.status === 200) {
          this.feedbackContent = result.data;
          $("#contentModal").modal();
        }
        else alert(result.message);
      }).
      catch(resp =>{
        alert("请求失败，请稍后重试");
      });
      $("#contentModal").modal();
    },
    frontPage() {
      if (this.page == 1) return;
      $('#' + this.page + 'page').removeClass("active");
      this.page = this.page - 1;
        this.getData();
      $('#' + this.page + 'page').addClass("active");
    },
    nextPage() {
      if (this.page == this.maxPage) return;
      $('#' + this.page + 'page').removeClass("active");
      this.page = this.page + 1;
        this.getData();

      $('#' + this.page + 'page').addClass("active");
    },
    turnTo(page) {
      $('#' + this.page + 'page').removeClass("active");
      this.page = page;
        this.getData();
      $('#' + this.page + 'page').addClass("active");
    },
    getData() {
      let data = {
        page:this.page,
        dealStatus:this.dealStatus
      }
      this.$http.post("/feedbackDataList",data).then(resp =>{
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
        dealStatus:this.dealStatus
      }
      this.$http.post("/getFeedbackMaxPage",data).then(resp =>{
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


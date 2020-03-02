var Board = new Vue({
  el: '#app',
  data: {
    name: '',
    title: '',
    content: '',
    boardList: [],
    selectedBoard: null,
    initialModel: {},
    totalPageLength: 0
  },
  mounted: function () {
    // this.getPages();
    this.onPageSelect(1);
  },
  methods: {
    initModel: function () {
      this.name = ''
      this.title = ''
      this.content = ''
    },
    onSubmit: function () {
      var self = this;
      var userModel = {
        name: self.name,
        title: self.title,
        content: self.content
      }

      $.ajax({
        url: '/api/board/register',
        type: 'POST',
        data: userModel,
        success: function (res) {
          console.log('성공')
          console.log(res)
          self.initModel();
          // self.getList();
          self.onPageSelect(1);

        },
        error: function (res) {
          console.log('에러')
          console.log(res)
          // alert(res.responseJSON.errors[0].defaultMessage)
        }
      })
    },

    onSubmitModified: function (idx) {
      var self = this;
      var userModel = {
        name: self.name,
        title: self.title,
        content: self.content
      }

      $.ajax({
        url: '/api/board/modify?idx=' + idx,
        type: 'PUT',
        data: userModel,
        success: function (res) {
          console.log('성공')
          console.log(res)
          self.initModel();
          // self.getList();
          self.onPageSelect(1);
          self.onSelect(self.selectedBoard.idx);
        },
        error: function (res) {
          console.log('에러')
          console.log(res)
          // alert(res.responseJSON.errors[0].defaultMessage)
        }
      })
    },

    onModify: function (board) {
      console.log('modify' + board)
      this.name = board.name
      this.title = board.title
      this.content = board.content

    },

    onDelete: function (idx) {
      console.log('delete' + idx)
      var self = this;
      
      $.ajax({
        url: '/api/board/delete?idx=' + idx,
        type: 'DELETE',
        success: function (res) {
          console.log('성공')
          console.log(res)
          self.selectedBoard = null
          // self.getList();
          self.onPageSelect(1);
        },
        error: function (res) {
          console.log('에러')
          console.log(res)
          // alert(res.responseJSON.errors[0].defaultMessage)
        }
      })
    },

    getList: function () {
      var self = this;
      $.ajax({
        url: '/api/board/list',
        type: 'GET',
        success: function (res) {
          self.boardList = res.data.list
        },
        error: function (res) {
          console.log(res)
        }
      })
    },

    getPages: function () {
      var self = this;
      $.ajax({
        url: '/api/board/pages?pNo=1&size=5',
        type: 'GET',
        success: function (res) {
          self.boardList = res.data.list
        },
        error: function (res) {
          console.log(res)
        }
      })
    },

    onPageSelect: function(idx) {
      var self = this;
      $.ajax({
        url: '/api/board/pages?size=5&pNo=' + (idx-1),
        type: 'GET',
        success: function (res) {
          console.log(res)
          self.totalPageLength = res.data.total
          if(res.data.list.length == 0){
            alert('없는 페이지입니다.')
          } else if(res.data.list.length < 5) {
            var leng = 6 - res.data.list.length;
            self.boardList = res.data.list
            for(var i=1; i < leng; i++) {
              self.boardList.push({
                idx: '',
                name: '',
                title: '',
                content: '',
                regDate: ''
              })

              // console.log('i= ' + i)
              
            }
          } else {
            self.boardList = res.data.list
          }
        },
        error: function (res) {
          console.log(res)
        }
      })
    },

    onSelect: function (idx) {
      var self = this;
      // console.log(idx);
      if(idx == ''){
        return false;
      }
      location.href = "/board/item/" + idx
      /* $.ajax({
        url: '/api/board/record?idx=' + idx,
        type: 'GET',
        success: function (res) {
          console.log(res)
          self.selectedBoard = res.data;
        },
        error: function (res) {
          console.log(res)
        }
      }) */
      // console.log(idx)
    }
  }
})
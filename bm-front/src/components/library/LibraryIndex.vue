<template>
  <el-container>
    <el-aside style="width: 200px;margin-top: 20px">
      <switch></switch>
      <SideMenu @indexSelect="listByCategory" ref="sideMenu"></SideMenu>
    </el-aside>
    <el-main>
      <books class="books-area" ref="booksArea"></books>
    </el-main>
  </el-container>
</template>

<script>
import SideMenu from './SideMenu'
import Books from './Books'

export default {
  name: 'AppLibrary',
  components: {Books, SideMenu},
  data () {
    return {
      currentPage: 1,
      totalBookNums: 0
    }
  },
  methods: {
    listByCategory () {
      var _this = this
      var cid = this.$refs.sideMenu.cid
      var url = 'categories/' + cid + '/books/' + _this.currentPage
      this.$axios.get('/books/count/' + cid).then(res => {
        console.log('图书总数:')
        console.log(res.data)
        _this.totalBookNums = res.data
        _this.$refs.booksArea.totalBookNums = _this.totalBookNums
      })
      this.$axios.get(url).then(res => {
        if (res && res.status === 200) {
          _this.$refs.booksArea.books = res.data
        }
      })
    }
  }
}
</script>

<style scoped>
.books-area {
  width: 990px;
  margin-left: auto;
  margin-right: auto;
}
</style>

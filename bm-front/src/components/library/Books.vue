<template>
  <div>
    <el-row style="height: 500px;">
      <search-bar @onSearch="searchResult" ref="searchBar"></search-bar>
      <el-tooltip effect="dark" placement="right"
                  v-for="item in books"
                  :key="item.id">
        <p slot="content" style="font-size: 16px;margin-bottom: 6px;">{{item.title}}</p>
        <p slot="content" style="font-size: 14px;margin-bottom: 6px">
          <span>{{item.author}}</span> /
          <span>{{item.date}}</span> /
          <span>{{item.press}}</span>
        </p>
        <p slot="content" style="width: 300px" class="abstract">{{item.abs}}</p>
        <el-card style="width: 135px;margin-bottom: 20px;height: 233px;float: left;margin-right: 15px" class="book"
                 bodyStyle="padding:10px" shadow="hover">
          <div class="cover" @click="editBook(item)">
            <img :src="item.coverPath" alt="封面">
          </div>
          <div class="info">
            <div class="title">
              <a href="">{{item.title}}</a>
            </div>
            <i class="el-icon-delete" @click="deleteBook(item)"></i>
          </div>
          <div class="author">{{item.author}}</div>
        </el-card>
      </el-tooltip>
      <edit-form @onSubmit="loadBooks()" ref="edit"></edit-form>
    </el-row>
    <el-row>
      <el-pagination
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-size="pagesize"
        :total="totalBookNums">
      </el-pagination>
    </el-row>
  </div>
</template>

<script>
import EditForm from './EditForm'
import SearchBar from './SearchBar'
export default {
  name: 'Books',
  components: {EditForm, SearchBar},
  data () {
    return {
      books: [],
      currentPage: 1,
      pagesize: 5,
      totalBookNums: 0
    }
  },
  mounted: function () {
    this.loadBooks()
  },
  methods: {
    loadBooks () {
      console.log('loadBooks at page: ' + this.currentPage)
      var _this = this

      this.$axios.get('/books/count/0').then(res => {
        console.log('图书总数:')
        console.log(res.data)
        _this.totalBookNums = res.data
      })

      this.$axios.get('/books/page/' + this.currentPage).then(res => {
        if (res && res.status === 200) {
          console.log('loadBooks at page:' + this.currentPage + '成功')
          _this.books = res.data
          console.log(res.data)
        }
      })
    },
    handleCurrentChange: function (currentPage) {
      this.currentPage = currentPage
      console.log(this.currentPage)

      var _this = this
      this.$axios.get('/books/page/' + this.currentPage).then(res => {
        if (res && res.status === 200) {
          console.log('loadBooks at page:' + this.currentPage + '成功')
          _this.books = res.data
          console.log(this.books)
        }
      })
    },
    searchResult () {
      console.log('搜索：' + this.$refs.searchBar.keywords)
      var _this = this
      this.$axios
        .get('/search?keyword=' + this.$refs.searchBar.keywords, {
        }).then(res => {
          if (res && res.status === 200) {
            _this.books = res.data
          }
        })
    },
    deleteBook (item) {
      this.$confirm('将删除图书, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        console.log('delete')
        console.log({
          id: item.id,
          coverPath: item.coverPath
        })
        this.$axios
          .post('/books/delete', {
            id: item.id,
            coverPath: item.coverPath
          }).then(res => {
            if (res && res.status === 200) {
              this.loadBooks()
            }
          })
      }
      ).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
      // alert(id)
    },
    editBook (item) {
      console.log('edit book')
      console.log(item)
      this.$refs.edit.dialogFormVisible = true
      this.$refs.edit.form = {
        id: item.id,
        coverPath: item.coverPath,
        title: item.title,
        author: item.author,
        date: item.date,
        press: item.press,
        abs: item.abs,
        cid: item.cid,
        mode: 'edit'
      }
    }
  }
}
</script>
<style scoped>

.cover {
  width: 115px;
  height: 172px;
  margin-bottom: 7px;
  overflow: hidden;
  cursor: pointer;
}

img {
  width: 115px;
  height: 172px;
  /*margin: 0 auto;*/
}

.title {
  font-size: 14px;
  text-align: left;
}

.author {
  color: #333;
  width: 102px;
  font-size: 13px;
  margin-bottom: 6px;
  text-align: left;
}

.abstract {
  display: block;
  line-height: 17px;
}

.el-icon-delete {
  cursor: pointer;
  float: right;
}

.switch {
  display: flex;
  position: absolute;
  left: 780px;
  top: 25px;
}

a {
  text-decoration: none;
}

a:link, a:visited, a:focus {
  color: #3377aa;
}

</style>

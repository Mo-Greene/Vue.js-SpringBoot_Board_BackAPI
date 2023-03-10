<template>
  <div>
    <table>
      <thead>
        <tr>
          <th>Title</th>
          <th>Content</th>
          <th>Writer</th>
        </tr>
      </thead>
      <tbody>
        <tr :key="i" v-for="(board,i) in boardList">
          <td>{{ board.resultData.boardTitle }}</td>
          <td>{{ board.resultData.boardContent }}</td>
          <td>{{ board.resultData.boardWriter }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  data () {
    return {
      boardList: []
    }
  },
  created () {
    this.getBoardList()
  },
  methods: {
    async getBoardList () {
      this.boardList = await this.$api('http://localhost:8080/boards/list', 'get')
      console.log(this.getBoardList())
    }
  }
}
</script>

<style scoped>

</style>

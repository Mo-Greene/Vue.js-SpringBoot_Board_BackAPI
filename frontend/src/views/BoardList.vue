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
          <td>{{ board.resultData.board.boardTitle }}</td>
          <td>{{ board.resultData.board.boardContent }}</td>
          <td>{{ board.resultData.board.boardWriter }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        boardList: []
      };
    },
    created() {
      this.getList();
    },
    methods: {
      async getList() {
        this.boardList = await this.$api('http://localhost:8080/boards/list', 'get');
        console.log(this.boardList)
      }
    }
  }
</script>

<style scoped>
  table {
    font-family: arial, sans-serif;
    border-collapse: collapse;
    width: 100%;
  }
  td, th {
    border: 1px solid #dddddd;
    text-align: left;
    padding: 8px;
  }
</style>

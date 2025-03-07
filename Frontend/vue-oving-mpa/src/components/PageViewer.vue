<script>
export default {
  created() {
    this.page = this.$pages.getSinglePage(this.$route.params.index);
  },
  data() {
    return {
      page: null
    };
  },
  watch: {
    '$route.params.index': 'fetchPage'
  },
  methods: {
    fetchPage() {
      this.page = this.$pages.getSinglePage(this.$route.params.index);
    }
  }
}

</script>

<template>
  <div v-if="page" class="container">
    <h1 class = "emphasize"> {{page.pageTitle}}</h1>
    <p> {{page.content}} </p>
  </div>
</template>

<style scoped>
.emphasize {
  color: blue;
  }
</style>

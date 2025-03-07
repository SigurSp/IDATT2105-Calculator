<template>
  <li>
    <router-link
        :to="page.link.url === 'calculator.vue' ? '/calculator' : 'Contact.vue' ? '/contact' : '/' + index"
        class="nav-link"
        :class= "activeClasses"
        aria-current="page"
        :href="page.link.url"
        title="'This link goes to the ${page.link.text} page'"
    >{{ page.link.text }}</router-link>
  </li>

</template>


<script>
export default {
  props: ['page', 'index', 'isActive'],
  computed: {
    activeClasses() {
      return {
        emphasize: this.isActive,
        active: this.isActive
      };
    }
  }
}
</script>

<style scoped>
.emphasize {
  text-decoration: underline !important;
}
</style>

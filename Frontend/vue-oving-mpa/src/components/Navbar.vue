<script>
import NavbarLink from "./navbarLink.vue";
import { authState } from '@/eventBus';
export default {
  components: {
    NavbarLink
  },
  created() {
    this.getThemeSetting();
  },
  props: ['pages', 'activePage'],
  data () {
    return {
      theme: 'light'
    }
  },
  computed: {
    isLoggedIn() {
      return authState.isLoggedIn;
    }
  },
  methods: {
    changeTheme() {
      let theme = 'light';
      if (this.theme === 'light') {
        theme = 'dark';
      }
      this.theme = theme;
      this.storeThemeSettings();
    },
    storeThemeSettings() {
      localStorage.setItem('theme', this.theme);
    },
    getThemeSetting() {
      let theme = localStorage.getItem('theme');
      if (theme) {
        this.theme = theme;
      }
    },
    logOut(){
      localStorage.removeItem('jwtToken');
      authState.isLoggedIn = false;
      this.$router.push('/login')
    }
  }
}
</script>

<template>
  <nav :class="[`navbar-${theme}`, `bg-${theme}`, 'navbar', 'navbar-expand-lg']">
    <div class="container-fluid">
      <a class="navbar-brand" href="#">My Vue </a>
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">

        <navbar-link
            v-for="(page, index) in pages"
            class="nav-item"
            :key="index"
            :page="page"
            :index="index"
            :isActive="index === activePage"
        ></navbar-link>

      </ul>
      <form class="d-flex">
        <button
          class="btn btn-primary"
          @click.prevent="changeTheme()"
          >Toggle</button>
        <button
          v-if="isLoggedIn"
          class="btn btn-danger ms-2"
          @click.prevent="logOut"
          >Log out </button>
      </form>
    </div>
  </nav>
</template>

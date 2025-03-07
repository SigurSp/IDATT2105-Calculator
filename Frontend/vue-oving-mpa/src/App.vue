<template>
  <navbar
      :pages="pages"
      :activePage="activePage"
  ></navbar>

  <router-view></router-view>
</template>

<script>
import PageViewer from '../src/components/PageViewer.vue'
import Navbar from '../src/components/Navbar.vue'
import Calculator from '../src/components/Calculator.vue'
import Contact from "@/components/Contact.vue";
import { authState } from '@/eventBus';
import { computed } from 'vue';

const isLoggedIn = computed(() => authState.isLoggedIn);

export default {
  components: {
    Navbar,
    PageViewer,
    Calculator,
    Contact
  },
  created() {
    this.getPages();
    this.checkToken();
    if (authState.isLoggedIn) {
      this.$router.push('/calculator')
    }
  },
  data () {
    return {
      activePage: 0,
      pages: []
    }
  },
  methods: {
    async getPages() {
      let res = await fetch('pages.json')
      let data = await res.json()

      this.pages = data;
    },
    checkToken() {
      const token = localStorage.getItem('jwtToken');
      if (token) {
        try {
          const payload = JSON.parse(atob(token.split('.')[1]));
          const expiry = payload.exp * 1000;
          if (Date.now() < expiry) {
            authState.isLoggedIn = true;
          } else {
            localStorage.removeItem('jwtToken');
            authState.isLoggedIn = false;
          }
        } catch (error) {
          console.error('Invalid token format:', error);
          localStorage.removeItem('jwtToken');
          authState.isLoggedIn = false;
        }
      }
    }
  }
}
</script>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { authState } from '@/eventBus'; // Ensure correct path to eventBus.js

const username = ref('');
const password = ref('');
const router = useRouter();

const login = async () => {
  try {
    const response = await fetch('http://localhost:8088/api/calculator/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      body: new URLSearchParams({ username: username.value, password: password.value }),
    });

    if (response.ok) {
      const token = await response.text();
      console.log('Received token:', token); // Log the token
      if (!token || !token.includes('.')) {
        console.error('Token is not a valid JWT:', token);
      }
      localStorage.setItem('jwtToken', token);
      authState.isLoggedIn = true; // Set authState
      router.push('/calculator');
    } else {
      console.log('Login failed with status:', response.status, 'Response:', await response.text());
      alert('Login failed');
    }
  } catch (error) {
    console.error('Login error:', error);
    alert('An error occurred during login');
  }
};
</script>

<template>
  <div>
    <input v-model="username" placeholder="Username" />
    <input v-model="password" type="password" placeholder="Password" />
    <button @click="login">Login</button>
  </div>
</template>

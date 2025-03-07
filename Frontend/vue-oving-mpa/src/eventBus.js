// src/eventBus.js
import { reactive } from 'vue';

export const authState = reactive({
    isLoggedIn: false,
});

export function setLoggedIn(status) {
    authState.isLoggedIn = status;
}

import { defineConfig } from 'vitest/config';
import vue from '@vitejs/plugin-vue';
import path from 'path';

export default defineConfig({
    test: {
        environment: 'jsdom', // Nødvendig for å teste Vue-komponenter
        globals: true,
    },
    plugins: [vue()],
    resolve: {
        alias: {
            '@': path.resolve(__dirname, './src'), // Viktig alias-konfigurasjon
        },
    },
});

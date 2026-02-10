import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
    plugins: [react()],
    resolve: { extensions: ['.js', '.jsx'] },
    server: {
        proxy: {
            '/api': {  //toutes les requêtes qui commencent par /api vont être redirigées vers ton backend SpringBoot
                target: 'http://localhost:8080', // ton Spring Boot
                changeOrigin: true
            }
        }
    }
})


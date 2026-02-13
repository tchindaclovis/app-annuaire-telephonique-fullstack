import axios from 'axios'  // l'instance axios est configuré dans le fichier http.js

// Grâce au proxy Vite, on peut utiliser directement '/api'
export const http = axios.create({ /* http devient ma porte d'entrée pour toutes les requêtes api*/
    baseURL: '/api',
    headers: { 'Content-Type': 'application/json' }
})

// Intercepteur (optionnel) pour log ou gérer erreurs
http.interceptors.response.use(
    (res) => res,
    (err) => {
        console.error('API error:', err.response?.data || err.message) /* si l'api renvoit une
        erreur je vais l'avoir dans la console*/
        return Promise.reject(err)
    }
)

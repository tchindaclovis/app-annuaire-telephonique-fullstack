import { http } from '../http'

// LISTER
export async function getContacts() {
    const { data } = await http.get('/contacts')
    return data
}

// CHERCHER par nom
export async function searchContacts(q) {
    const { data } = await http.get('/contacts/search', { params: { q } })
    return data
}

// CREER
export async function createContact(dto) {
    const { data } = await http.post('/contacts', dto)
    return data
}

// METTRE A JOUR
export async function updateContact(id, dto) {
    const { data } = await http.put(`/contacts/${id}`, dto)
    return data
}

// SUPPRIMER
export async function deleteContact(id) {
    await http.delete(`/contacts/${id}`)
}

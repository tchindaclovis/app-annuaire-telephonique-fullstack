import React, { useEffect, useState } from 'react'
import { getContacts, createContact, deleteContact, updateContact, searchContacts } from './api/contactsApi'
import ContactForm from './components/ContactForm'
import ContactRow from './components/ContactRow'

export default function App() {  //composant principal
    const [contacts, setContacts] = useState([])  //liste des contacts
    const [loading, setLoading] = useState(true)  //l'état de chargement
    const [error, setError] = useState('')  // un message d'erreur éventuel
    const [query, setQuery] = useState('')   // la recherche par nom

    //on a défini une fonction load qui va chercher les données
    async function load() {
        try {
            setLoading(true)
            const data = query ? await searchContacts(query) : await getContacts() /*si query n'est pas vide
             on va rechercher sinon on récupère tout et on gère les erreurs éventuelles*/
            setContacts(data)
        } catch (e) {
            setError("Impossible de charger les contacts")
        } finally {
            setLoading(false)
        }
    }

    /*va permettre de recharger la liste lorsqu'on tape dans la barre de recherche*/
    useEffect(() => { load() }, [query])


    /*on force l'id=1 en attendant la vraie authentification*/
    async function handleCreate(dto) {
        // dto doit contenir utilisateurId (temporaire : 1)
        if (!dto.utilisateurId) dto.utilisateurId = 1
        const created = await createContact(dto)
        setContacts((prev) => [created, ...prev])
    }


    /*permet de supprimer localement un contact après suppression coté backend*/
    async function handleDelete(id) {
        await deleteContact(id)
        setContacts((prev) => prev.filter(c => c.id !== id))
    }


    /*permet de modifier un contact*/
    async function handleUpdate(id, dto) {
        const updated = await updateContact(id, dto)
        setContacts((prev) => prev.map(c => c.id === id ? updated : c))
    }


    return (
        <div style={{ maxWidth: 900, margin: '40px auto', fontFamily: 'Arial, sans-serif' }}>
            <h1>Annuaire — Gestion des contacts</h1>

            <div style={{ margin: '12px 0' }}>
                <input
                    placeholder="Rechercher par nom…"
                    value={query}
                    onChange={(e) => setQuery(e.target.value)}
                    style={{ padding: 8, width: 300 }}
                />
            </div>


            <ContactForm onCreate={handleCreate} />

            {loading && <p>Chargement…</p>}
            {error && <p style={{ color: 'red' }}>{error}</p>}

            {!loading && contacts.length === 0 && <p>Aucun contact.</p>}

            <div>
                {contacts.map(c => (
                    <ContactRow key={c.id} contact={c} onDelete={handleDelete} onUpdate={handleUpdate} />
                ))}
            </div>
        </div>
    )
}

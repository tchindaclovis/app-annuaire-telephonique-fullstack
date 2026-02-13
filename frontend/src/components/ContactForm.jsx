import React, { useState } from 'react'  //état pour stocker la valeur des inputs

export default function ContactForm({ onCreate }) {  /*une fonction onCreate dans laquelle
on a les différents états controllé pour chaque champ du formulaire*/
    const [nom, setNom] = useState('')
    const [prenom, setPrenom] = useState('')
    const [telephone, setTelephone] = useState('')
    const [email, setEmail] = useState('')
    const [intitulePoste, setIntitulePoste] = useState('')
    const [direction, setDirection] = useState('')
    const [bureau, setBureau] = useState('')

    //fonction de soumission
    async function handleSubmit(e) {
        e.preventDefault()  /*1-on empêche le rechargement de la page*/
        if (!nom || !prenom || !telephone) { /*2-pour valiser le formulaire on a besoin de 3 champs obligatoires*/
            alert('Nom, prénom et téléphone sont obligatoires')
            return
        }

        /*3-on appelle onCreate avec un dto*/
        await onCreate({nom, prenom, telephone, email, intitulePoste, direction, bureau, utilisateurId: 1})
        /*4-on reinitialise le formulaire*/
        setNom('');
        setPrenom('');
        setTelephone('');
        setEmail('');
        setIntitulePoste('');
        setDirection('');
        setBureau('')
    }

    //Mise en page
    return (
        <form onSubmit={handleSubmit} style={{ border: '1px solid #eee', padding: 12, margin: '12px 0' }}>
            <h3>Ajouter un contact</h3>
            {/*grid de 3 colonnes pour des champs bien alignés*/}
            {/*l'ensemble de tous les inputs qui vont être controllés et à chaque onChange,
            on va mettre à jour la valeur correspondante*/}
            <div style={{ display: 'grid', gridTemplateColumns: 'repeat(3, 1fr)', gap: 8 }}>
                <input placeholder="Nom *" value={nom} onChange={e => setNom(e.target.value)} />
                <input placeholder="Prénom *" value={prenom} onChange={e => setPrenom(e.target.value)} />
                <input placeholder="Téléphone *" value={telephone} onChange={e => setTelephone(e.target.value)} />
                <input placeholder="Email" value={email} onChange={e => setEmail(e.target.value)} />
                <input placeholder="Intitulé de poste" value={intitulePoste} onChange={e => setIntitulePoste(e.target.value)} />
                <input placeholder="Direction" value={direction} onChange={e => setDirection(e.target.value)} />
                <input placeholder="Bureau" value={bureau} onChange={e => setBureau(e.target.value)} />
            </div>

            {/*le bouton submit qui va déclencher handleSubmit*/}
            {/*lorsqu'on clique sur "créer", la fonction handleSubmit va
            1-empêcher le rechargement de la page
            2-vérifie que les champs obligatoires sont remplis
            3-appelle onCreate avec les données du contact
            4-réinitialise le formulaire*/}
            <button type="submit" style={{ marginTop: 10 }}>Créer</button>
        </form>
    )
}

import React, { useState } from 'react' /*on a besoin d'état pour passer du mode lecture au mode édition*/

export default function ContactRow({ contact, onDelete, onUpdate }) { /*une fonction qui reçoit en paramètre
d'autre contact*/
    const [edit, setEdit] = useState(false) /*mode édition par défaut désactivé*/
    const [form, setForm] = useState({ ...contact })  /*copie éditable du contact*/

    function change(k, v) { setForm({ ...form, [k]: v }) } /*utilitaire qui va permettre de mettre à jour
    un champ du formulire*/

    async function save() {  /*va appeler le backend via la methode onUpdate*/
        await onUpdate(contact.id, {
            nom: form.nom,
            prenom: form.prenom,
            telephone: form.telephone,
            email: form.email,
            intitulePoste: form.intitulePoste,
            direction: form.direction,
            bureau: form.bureau,
            utilisateurId: form.utilisateur?.id // si tu autorises le transfert
        })
        setEdit(false)  /*on quitte le mode édition sachant qu'en mode lecture
        on affiche seulement le nom, le prénom, le téléphone et les infos du poste */
    }

    return (
        <div style={{ border: '1px solid #ddd', padding: 10, marginBottom: 8 }}>
            {!edit ? (
                <>
                    <div><b>{contact.nom} {contact.prenom}</b> — {contact.telephone}</div>
                    <div style={{ fontSize: 13, color: '#555' }}>
                        {contact.intitulePoste || '-'} · {contact.direction || '-'} · {contact.bureau || '-'}
                    </div>
                    <div style={{ marginTop: 6 }}>
                        <button onClick={() => setEdit(true)}>Modifier</button>
                        {/*le bouton supprimer appelle la methode onDelete*/}
                        <button onClick={() => onDelete(contact.id)} style={{ marginLeft: 8 }}>Supprimer</button>
                    </div>
                </>
            ) : (
                <div style={{ display: 'grid', gridTemplateColumns: 'repeat(3,1fr)', gap: 6 }}>
                    <input value={form.nom} onChange={e => change('nom', e.target.value)} />
                    <input value={form.prenom} onChange={e => change('prenom', e.target.value)} />
                    <input value={form.telephone} onChange={e => change('telephone', e.target.value)} />
                    <input value={form.email || ''} onChange={e => change('email', e.target.value)} />
                    <input value={form.intitulePoste || ''} onChange={e => change('intitulePoste', e.target.value)} />
                    <input value={form.direction || ''} onChange={e => change('direction', e.target.value)} />
                    <input value={form.bureau || ''} onChange={e => change('bureau', e.target.value)} />
                    <div style={{ gridColumn: '1 / -1', marginTop: 6 }}>
                        <button onClick={save}>Enregistrer</button>
                        <button onClick={() => setEdit(false)} style={{ marginLeft: 8 }}>Annuler</button>
                    </div>
                </div>
            )}
        </div>
    )
}

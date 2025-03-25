const admin = require("./firebase");

const setAdminRole = async (userId) => {
    try {
        const userRef = admin.firestore().collection("users").doc(userId);
        await userRef.update({ role: "admin" });
        console.log(`✅ Usuario ${userId} ahora es administrador.`);
    } catch (error) {
        console.error("❌ Error al actualizar el rol:", error);
    }
};

// Reemplaza con el UID del usuario "ninichuexpress@gmail.com"
setAdminRole("bz67H6nOROPsCvfksd6HYeufQ9r1");

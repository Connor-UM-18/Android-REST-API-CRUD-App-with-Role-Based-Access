const admin = require("../utils/firebase");
const jwt = require("jsonwebtoken");

const verifyToken = async (req, res, next) => {
  const token = req.headers.authorization?.split(" ")[1];

  if (!token) {
      return res.status(401).json({ message: "Token no proporcionado" });
  }

  try {
      const decodedToken = jwt.verify(token, "tu_secreto_secreto"); // Usa una clave secreta segura
      req.uid = decodedToken.uid;

      // Obtener el rol del usuario desde Firestore
      const userDoc = await admin.firestore().collection("users").doc(req.uid).get();

      if (!userDoc.exists) {
          return res.status(404).json({ message: "Usuario no encontrado" });
      }

      req.user = { uid: req.uid, role: userDoc.data().role }; // Agregar `role` a `req.user`
      next();
  } catch (error) {
      return res.status(401).json({ message: "Token inválido" });
  }
};

module.exports = verifyToken;
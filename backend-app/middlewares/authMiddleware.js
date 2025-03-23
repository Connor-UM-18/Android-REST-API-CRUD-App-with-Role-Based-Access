const admin = require("../utils/firebase");
const jwt = require("jsonwebtoken");

const verifyToken = async (req, res, next) => {
  const token = req.headers.authorization?.split(" ")[1];

  if (!token) {
    return res.status(401).json({ message: "Token no proporcionado" });
  }

  try {
    const decodedToken = jwt.verify(token, "tu_secreto_secreto"); // Reemplaza "tu_secreto_secreto" con una clave secreta segura
    req.uid = decodedToken.uid;
    next();
  } catch (error) {
    return res.status(401).json({ message: "Token inválido" });
  }
};

module.exports = verifyToken;
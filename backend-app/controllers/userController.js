const admin = require("../utils/firebase");

const getUser = async (req, res) => {
  try {
    const userRecord = await admin.auth().getUser(req.uid);
    res.status(200).json(userRecord);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

module.exports = { getUser };
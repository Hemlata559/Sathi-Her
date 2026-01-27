const express = require('express');
const router = express.Router();
const {
  getAddressCoordinate,
  getDistanceTime
} = require('../services/maps.service');
const { getAutoCompleteSuggestions } = require('../services/maps.service');

router.get('/get-coordinates', async (req, res) => {
  try {
    const { address } = req.query;
    const data = await getAddressCoordinate(address);
    res.json(data);
  } catch (err) {
    res.status(400).json({ error: err.message });
  }
});

router.get('/get-distance-time', async (req, res) => {
  try {
    const { origin, destination } = req.query;

    const originCoords = await getAddressCoordinate(origin);
    const destinationCoords = await getAddressCoordinate(destination);

    const result = await getDistanceTime(originCoords, destinationCoords);

    res.json(result);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

router.get('/autocomplete', async (req, res) => {
  try {
    const { input } = req.query;

    const suggestions = await getAutoCompleteSuggestions(input);
    res.json(suggestions);
  } catch (err) {
    res.status(400).json({ error: err.message });
  }
});

module.exports = router;

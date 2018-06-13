UPDATE components SET component_name = 'Chat' WHERE component_id = 'bct-chat';
UPDATE components SET component_name = 'CryptoMarketCap' WHERE component_id = 'cryptomarketcap-info';
UPDATE components SET component_name = 'Spreadsheet' WHERE component_id = 'spreadsheets';
UPDATE components SET component_name = 'Heatmap - Coins' WHERE component_id = 'heatmap-coins';
UPDATE components SET component_name = 'Heatmap - Exchanges' WHERE component_id = 'heatmap-exchanges';
UPDATE components SET component_name = 'Heatmap - Trend' WHERE component_id = 'heatmap-trend';
UPDATE components SET component_name = 'Heatmap - Category' WHERE component_id = 'heatmap-category';
UPDATE components SET component_name = 'Heatmap - Volatility' WHERE component_id = 'heatmap-volatility';
UPDATE components SET
  component_name = 'Heatmap - Unusual volume',
  url = 'https://bct-heatmaps.app.cgblockchain.com/unusual_volume'
  WHERE component_id = 'heatmap-unusual-volume';
DELETE FROM components;
DELETE FROM apps;

INSERT INTO apps (app_id, app_name, url, logo, enabled) VALUES (
  'trading',
  'Trading',
  '',
  '',
  true
);

INSERT INTO apps (app_id, app_name, url, logo, enabled) VALUES (
  'news-events',
  'News & Events',
  '',
  '',
  true
);

INSERT INTO apps (app_id, app_name, url, logo, enabled) VALUES (
  'social',
  'Social',
  '',
  '',
  true
);

INSERT INTO apps (app_id, app_name, url, logo, enabled) VALUES (
  'statistics-monitoring',
  'Statistics & Monitoring',
  '',
  '',
  true
);

INSERT INTO apps (app_id, app_name, url, logo, enabled) VALUES (
  'icos',
  'ICOs',
  '',
  '',
  true
);

INSERT INTO apps (app_id, app_name, url, logo, enabled) VALUES (
  'toolset',
  'Toolset',
  '',
  '',
  true
);

INSERT INTO apps (app_id, app_name, url, logo, enabled) VALUES (
  'visual-indicators',
  'Visual Indicators',
  '',
  '',
  true
);

-- Trading

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('trading-app', 'Trading App', 'trading', 'https://terminal.bctmm.io/', '', TRUE);

-- News & Events

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('news', 'News', 'news-events', 'https://bctnews.app.cgblockchain.com', '', TRUE);

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('cryptonews', 'Crypto News (Blockfolio)', 'news-events', 'https://bct-cryptonews.app.cgblockchain.com', '', TRUE);

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('sec-news', 'SEC News', 'news-events', 'https://bct-secgov.app.cgblockchain.com/', '', TRUE);

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('tv', 'TV', 'news-events', 'https://bct-apps-sandbox.app.cgblockchain.com/tv/', '', TRUE);

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('calendar', 'Calendar', 'news-events', 'https://calendar.app.cgblockchain.com', '', TRUE);

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('coins-calendar', 'Coins Calendar', 'news-events', 'https://calendar.app.cgblockchain.com/agenda/', '', TRUE);

-- Social

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('telegram-messages', 'Telegram Messages', 'social', 'https://bct-telegram.app.cgblockchain.com/', '', TRUE);

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('bct-chat', 'BCT Chat', 'social', 'https://chat2.bct.io/channel/general', '', TRUE);

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('tags-cloud', 'Tags Cloud', 'social', 'https://bct-tags-cloud.app.cgblockchain.com/', '', TRUE);

-- Statistics & Monitoring

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('cryptomarketcap-info', 'CryptoMarketCap Info', 'statistics-monitoring', 'https://bct-cryptomarketcap.app.cgblockchain.com', '', TRUE);

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('coins-price-on-exchanges', 'Coins Price on Exchanges', 'statistics-monitoring', 'https://bct-apps-sandbox.app.cgblockchain.com/cryptocompare-exchange/', '', TRUE);

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('sanbase-stats', 'SANbase Stats', 'statistics-monitoring', 'https://bct-apps-sandbox.app.cgblockchain.com/cashflow', '', TRUE);

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('exchanges-rating', 'Exchanges Rating', 'statistics-monitoring', 'https://bct-exchanges.app.cgblockchain.com/', '', TRUE);

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('coins-correlation-matrix', 'Coins Correlation Matrix', 'statistics-monitoring', 'https://bct-apps-sandbox.app.cgblockchain.com/correlation', '', TRUE);

-- ICOs

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('ico-governance', 'ICO Governance', 'icos', 'https://ico-governance.app.cgblockchain.com/database', '', TRUE);

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('crypto-compare', 'CryptoCompare (coins list)', 'icos', 'https://bct-apps-sandbox.app.cgblockchain.com/cryptocompare', '', TRUE);

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('icos-gantt-chart', 'ICOs Gantt Chart', 'icos', 'https://bct-chart-ico.app.cgblockchain.com/', '', TRUE);

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('icos-list', 'ICOs List', 'icos', 'https://bct-chart-ico.app.cgblockchain.com/list', '', TRUE);

-- Toolset

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('coin-conversion', 'Coin Conversion', 'toolset', 'https://bct-apps-sandbox.app.cgblockchain.com/conversion', '', TRUE);

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('spreadsheets', 'Spreadsheets', 'toolset', 'https://bct-sheet.app.cgblockchain.com/', '', TRUE);

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('tech-analysis', 'Tech Analysis', 'toolset', 'https://charts.app.cgblockchain.com/markets/BTC_USD/exchanges/COINBASE', '', TRUE);

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('cco-logbook', 'CCO Logbook (ComplianceGuard)', 'toolset', 'https://client1.app.cgblockchain.com', '', TRUE);

-- Visual Indicators

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('coin-price-chart', 'Coin Price Chart', 'visual-indicators', 'https://bct-apps-sandbox.app.cgblockchain.com/cryptocurrency-price-chart', '', TRUE);

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('cryptocurrency-mini-charts', 'CryptoCurrency Mini Charts', 'visual-indicators', 'https://bct-markets.app.cgblockchain.com/', '', TRUE);

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('heatmap-coins', 'HeatMap Coins', 'visual-indicators', 'https://bct-heatmaps.app.cgblockchain.com', '', TRUE);

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('heatmap-exchanges', 'HeatMap Exchanges', 'visual-indicators', 'https://bct-heatmaps.app.cgblockchain.com/exchanges', '', TRUE);

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('heatmap-trend', 'HeatMap Trend', 'visual-indicators', 'https://bct-heatmaps.app.cgblockchain.com/trend', '', TRUE);

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('heatmap-category', 'HeatMap Category', 'visual-indicators', 'https://bct-heatmaps.app.cgblockchain.com/category', '', TRUE);

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('heatmap-volatility', 'HeatMap Volatility', 'visual-indicators', 'https://bct-heatmaps.app.cgblockchain.com/volatility', '', TRUE);

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('heatmap-unusual-volume', 'HeatMap Unusual Volume', 'visual-indicators', 'https://bct-heatmaps.app.cgblockchain.com/unusual-volume', '', TRUE);

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('crypto-ticker', 'Crypto Ticker', 'visual-indicators', 'https://bct-tickers.app.cgblockchain.com/cryptoticker', '', TRUE);

INSERT INTO components (component_id, component_name, app_id, url, logo, enabled) VALUES
  ('crypto-ticker-lite', 'Crypto Ticker Lite', 'visual-indicators', 'https://bct-tickers.app.cgblockchain.com/cryptocompare', '', TRUE);
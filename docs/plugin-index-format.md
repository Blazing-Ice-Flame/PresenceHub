# Plugin Index Format

The plugin repository must expose a JSON file at:

`plugin-index.json`

Example structure:

{
  "version": 1,
  "plugins": [
    {
      "pluginId": "youtube",
      "name": "YouTube",
      "version": "1.0.0",
      "apiVersion": 1,
      "downloadUrl": "https://github.com/Ombryal/PresenceHub-Plugins/releases/download/youtube-v1.0.0/youtube-plugin.apk",
      "checksumSha256": "sha256-here",
      "signature": "signature-here",
      "description": "Official YouTube Presence plugin.",
      "verified": true
    }
  ]
}

The main app reads this file and builds the plugin store from it.

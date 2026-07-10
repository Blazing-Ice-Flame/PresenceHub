# PresenceHub

PresenceHub is a modular Discord Rich Presence client for Android.

Instead of hardcoding support for YouTube, Spotify, Twitch, and every other platform into one massive app, PresenceHub works through plugins.

Each plugin is responsible for detecting activity, handling authentication, and generating Rich Presence information.

The main application simply manages plugins and communicates with Discord.

---

## Features

- Modular plugin architecture
- Discord Rich Presence
- Background service
- Plugin verification
- Automatic plugin updates
- GitHub-hosted plugin index
- Secure plugin loading
- Lightweight core

---

## Currently Supported

- YouTube

More providers will be added after the first stable release.

---

## Project Structure

Core App

- UI
- Plugin Manager
- RPC Manager
- Background Service
- Security
- Plugin Loader

Plugins

- YouTube
- (Future)
- Spotify
- Twitch
- Crunchyroll
- VLC
- Browser

---

## Security

PresenceHub verifies every plugin before loading.

Future versions will include

- SHA-256 verification
- Digital signatures
- API version checks
- Plugin sandboxing

---

## License

MIT License

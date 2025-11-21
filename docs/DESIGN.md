# Janus Glossa Design Manifest

> **Tagline**: "The Two-Faced Tongue" — Translating concepts, not just words.

This document outlines the branding, visual identity, and user interface design language for **Janus Glossa** (formerly Janus). It serves as the reference for all future UI/UX development.

## 1. Brand Identity

### Name: Janus Glossa
- **Etymology**: A hybrid of **Janus** (Roman god of transitions, doorways, and duality) and **Glossa** (Greek for tongue/language).
- **Rationale**: The name reflects the app's core mechanism—looking at a concept from two directions (Source & Target) to find its true contextual meaning.

### Tone
Academic, Trustworthy, Structural, "The Scholar's Tool."

### Core Concept: The "Interlanguage Pivot"
Unlike standard dictionaries that translate words (`Word A = Word B`), Janus Glossa aligns **concepts**.
1. **Find** the concept node (Wikipedia Article).
2. **Contextualize** the meaning.
3. **Pivot** via the Interlanguage Link to the target language.

## 2. Visual Language: "The Classicist"

The design aesthetic is inspired by physical archives: ink, parchment, gold, and slate. It should feel authoritative and premium.

> [!IMPORTANT]
> System-based Dynamic Colors (Material You) are explicitly **disabled** to ensure the "Classicist" theme is consistently applied across all devices.

### Color Palette
We use a dual-theme system to maintain the academic vibe in both lighting conditions.

#### 🌑 Dark Theme: "Gold on Slate"
Used for low-light environments. Evokes engraved stone and illuminated manuscripts.

| Token | Hex | Role |
| :--- | :--- | :--- |
| **Background** | `#121212` | Deep Slate (Base canvas) |
| **Surface** | `#242426` | Charcoal (Cards, bottom sheets) |
| **Primary** | `#FFD54F` | Illuminated Gold (Active tabs, FABs, Icons) |
| **On Primary** | `#1C1C1E` | Dark Ink (Text on Gold buttons) |
| **Text** | `#E6E1E5` | Bone / Off-White (Body text) |
| **Secondary** | `#3E3E42` | Steel Grey (Inactive elements) |

#### ☀️ Light Theme: "Ink on Parchment"
Used for day environments. Evokes a clean, warm library book.

| Token | Hex | Role |
| :--- | :--- | :--- |
| **Background** | `#F9F6F2` | Warm Alabaster / Parchment |
| **Surface** | `#FFFFFF` | Pure White (Cards, slight lift) |
| **Primary** | `#785A00` | Antique Bronze (Active tabs, Buttons) |
| **On Primary** | `#FFFFFF` | White (Text on Bronze buttons) |
| **Text** | `#1C1C1E` | Deep Ink (Body text) |
| **Secondary** | `#E8E0D5` | Beige / Tan (Inactive elements) |

## 3. Typography

We mix typeface styles to reinforce the "Dictionary" feel without sacrificing screen readability.

### Headings & Titles
- **Font Family**: **Merriweather** (or Playfair Display)
- **Usage**: App Bar titles, Result Card headings, Disambiguation list titles.
- **Why**: Serif fonts imply authority and distinctiveness.

### Body & Definitions
- **Font Family**: **Roboto** (System Default)
- **Usage**: Snippets, definitions, button text, input fields.
- **Why**: Sans-serif offers superior legibility for dense text at small sizes.

## 4. Iconography

### App Icon: "The Glyph Hub"
The icon represents the connection of languages through a central conceptual node.
- **Visual Elements**: Three characters from distinct writing systems radiating from a central hub.
    - Top: Latin **'A'**
    - Bottom Left: CJK **'文'** (Wén - Text/Culture)
    - Bottom Right: Greek **'Ω'** (Omega)
- **Style**: Minimalist, geometric lines.
- **Colors**: Gold lines (`#FFD54F`) on a Dark Slate background (`#121212`).

### UI Icons
- **Tinting**: All actionable vector assets (Copy, Open External, Share) must be tinted with the `colorPrimary` (Gold/Bronze) to indicate interactivity.

## 5. UI Component Guidelines

### The Home Screen (Empty State)
- **Goal**: Welcome the user to the "Concept Search."
- **Design**:
    - **Center**: Place the **App Icon** (or a monochromatic vector version) in the center.
    - **Hint Text**: Randomized welcome message (e.g., "Find a concept", "Translate a subject") to inspire curiosity.
    - **Background**: Solid Theme Background color.

### The Result Screen (The Pivot)
- **Goal**: Visualize the connection between Source and Target.
- **Layout**:
    - **Source Card (Top)**: Slightly recessed/dimmed background. Serif Title.
    - **Connector**: A visible vertical line or "Chain Link" icon connecting the two cards. Color: `colorPrimary`.
    - **Target Card (Bottom)**: Standard Surface background. Serif Title. Accentuated actions.

### Disambiguation List
- **Goal**: Help users choose the specific **meaning** (Concept) of a word.
- **Layout**:
    - **Title**: Serif Font (e.g., "Keyboard (Music)" vs "Keyboard (Computing)").
    - **Subtitle**: Sans-Serif snippets.
    - **Visuals**: Clean separation between items using standard Material spacing.

## 6. Assets & Social

### GitHub Social Card
- Follows the "Gold on Slate" aesthetic.
- Features the App Icon prominently.
- Includes the tagline: "The Two-Faced Tongue".

# TP POO — E-Commerce Java

## Description

Application e-commerce en ligne de commande développée en **Java 11**.  
Le projet illustre les principes de la **Programmation Orientée Objet** et les **Design Patterns** à travers un système de gestion de commandes.

## Fonctionnalités

- Catalogue de **10 produits** (physiques et digitaux)
- Génération de **commandes aléatoires** (client, produits, quantités)
- **Paiement** par CB ou PayPal
- **Livraison** standard ou express
- **Historique** des commandes

## Structure du projet

```
src/ecommerce/
├── model/          # Entités métier (Product, Order, Customer, CartItem, OrderStatus)
├── factory/        # Création d'objets (ProductFactory)
├── payment/        # Stratégies de paiement (PaymentMethod, CB, PayPal)
├── shipping/       # Stratégies de livraison (ShippingMethod, Standard, Express)
├── repository/     # Persistance des données (OrderRepository, InMemoryOrderRepository)
├── service/        # Logique métier (OrderService, CheckoutService)
├── UI/             # Interface console (ConsoleUI)
└── Main.java       # Point d'entrée
```

## Concepts POO utilisés

| Concept | Exemple dans le projet |
|---|---|
| **Héritage** | `PhysicalProduct` et `DigitalProduct` héritent de `Product` |
| **Abstraction** | `Product` est une classe abstraite avec `getType()` abstrait |
| **Interfaces** | `PaymentMethod`, `ShippingMethod`, `OrderRepository` |
| **Polymorphisme** | `method.pay()`, `shipping.ship()` — appels polymorphes |
| **Encapsulation** | Champs `private` / `protected`, accès via getters |

## Design Patterns

### Création — Factory
`ProductFactory` encapsule la création des produits via des méthodes statiques `createPhysical()` / `createDigital()`.

### Comportement — Strategy (×2)
- **Paiement** : `PaymentMethod` → `CreditCardPayment`, `PaypalPayment`
- **Livraison** : `ShippingMethod` → `StandardShipping`, `ExpressShipping`

### Structure — Repository
`OrderRepository` (interface) → `InMemoryOrderRepository` (implémentation HashMap).  
Découple l'accès aux données de la logique métier.

## Principes SOLID

| Principe | Application |
|---|---|
| **S** — Single Responsibility | Chaque classe a une seule responsabilité (ex: `OrderService` ≠ `CheckoutService`) |
| **O** — Open/Closed | Ajout d'un nouveau moyen de paiement sans modifier le code existant |
| **L** — Liskov Substitution | `StandardShipping` et `ExpressShipping` interchangeables via `ShippingMethod` |
| **I** — Interface Segregation | Interfaces spécialisées (`PaymentMethod`, `ShippingMethod`, `OrderRepository`) |
| **D** — Dependency Inversion | `OrderService` dépend de l'interface `OrderRepository`, pas de `InMemoryOrderRepository` |

## Prérequis

- **Java 11** ou supérieur

## Lancement

```bash
javac -d bin src/ecommerce/**/*.java src/ecommerce/*.java
java -cp bin ecommerce.Main
```

## Auteur

**LoicAND**

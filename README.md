# Tron AI – Game Decision Support Algorithms

![Java](https://img.shields.io/badge/Java-21+-red?style=flat&logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-UI-0f9d58?style=flat)
![Architecture](https://img.shields.io/badge/Architecture-MVC-blue?style=flat)
![Algorithms](https://img.shields.io/badge/AI-MaxN%20%7C%20Paranoid%20%7C%20SOS--inspired-6f42c1?style=flat)

A university project that reimplements a Tron / multi-player Snake game in **MVC** with a **JavaFX** interface, and multiple **decision algorithms**:
- MaxN (multi-player),
- Paranoid (all others assumed to collude),
- Team extensions inspired by SOS (cooperative evaluation/coordination for teams).

Benchmarks explore different algorithm combinations while varying **team sizes** and **grid dimensions**.

---

## Preview

![Tron Game preview](demo.gif)

---

## Features

- Clean **MVC** architecture with a **Controller** layer coordinating Model and View.
- **JavaFX** GUI for interactive runs; optional headless mode for benchmarks.
- Pluggable AIs: Minimax, MaxN, Paranoid, and team-aware SOS-inspired variants.
- Batch **benchmark harness** varying grid size, team size, search depth and sampling.
- Results export for post-analysis and plotting.

> [!TIP]
> Use headless runs for reproducible benchmarks and keep random seeds fixed when comparing algorithmic variants.

> [!IMPORTANT]
> Keep evaluation heuristics, time budgets and depth settings consistent across compared algorithms; otherwise results are not comparable.

---

## Project Structure

```text
src
└── game
    ├── algo
    ├── controller
    ├── data
    ├── exec
    ├── model
    │   ├── entities
    │   └── platform
    ├── util
    │   ├── config
    │   ├── mvc
    │   └── strategies
    └── view
```

- `model/`: Grid, players, teams, rules and terminal checks.
- `view/`: JavaFX rendering (board, HUD, controls).
- `controller/`: Input, game loop, tick scheduling, dispatch to view.
- `algo/` and `util/strategies/`: Search policies (Minimax, MaxN, Paranoid) and team/SOS augmentations.
- `util/config/`: Configuration parsing and validation.
- `exec/`: Launchers for GUI and batch benchmark modes.

---

## Configuration

All core parameters are set in `config.txt`. Example:

```ini
# platform size
platform_size = 50

# numbers of bots in both teams
nb_players_per_team = 2

# algorithm of the players of both teams : maxn / paranoid / sos
team_1_strat = maxn
team_2_strat= sos

# search depth of the algorithms
depth = 3

# simulation parameters
depth_steps = 2
depth_delta = 1
grid_size_steps = 2
grid_size_delta = 10
nb_players_steps = 2
nb_players_delta = 1
simulations_per_sample = 5
```

- `platform_size`: base grid dimension (square).  
- `nb_players_per_team`: agents per team.  
- `team_1_strat`, `team_2_strat`: strategy identifiers (`maxn`, `paranoid`, `sos`).  
- `depth`: base search depth.  
- Simulation sweep parameters (`*_steps`, `*_delta`) control how benchmarks iterate grid size, team size and depth; `simulations_per_sample` sets repetitions per setting.

> [!WARNING]
> Ensure values are valid and lead to feasible states (e.g., grid large enough for chosen team sizes). Invalid combinations may terminate early or throw validation errors.

---

## Build & Run

### Maven (example)
```bash
mvn clean package
# GUI run (ensure JavaFX plugin/deps configured in pom.xml)
mvn javafx:run -Dexec.args="--config config.txt --mode gui"
# Headless benchmark (fat jar)
java -jar target/tron-ai.jar --config config.txt --mode benchmark
```

### Gradle (example)
```bash
./gradlew clean build
# GUI run
./gradlew run --args="--config config.txt --mode gui"
# Headless benchmark
java -jar build/libs/tron-ai.jar --config config.txt --mode benchmark
```

> [!TIP]
> If your JDK does not bundle JavaFX, use the Maven `javafx-maven-plugin` or the Gradle `org.openjfx.javafxplugin` to manage modules and runtime options.

---

## Algorithms (summary)

- **Minimax**: two-player adversarial search; supports depth limits and alpha–beta pruning.  
- **MaxN**: N-player generalization with vector utilities; each agent maximizes its own component.  
- **Paranoid**: reduce multi-player to 2-player by assuming opponents collude; typically faster and conservative.  
- **SOS-inspired team play**: team-sharing of utility and coordination heuristics layered over base policies.

Heuristic features commonly include space/territory control, mobility, hazard proximity, and team coverage.

---

## Benchmarking

- Varies `platform_size`, `nb_players_per_team`, and `depth` using the `*_steps`/`*_delta` parameters.  
- Repeats each configuration `simulations_per_sample` times, aggregating win rate, survival ticks and territory metrics.  
- Outputs machine-readable results (CSV/JSON) for plotting.

> [!NOTE]
> Keep random seeds fixed across runs if you want fair comparisons. Only change one factor at a time (e.g., algorithm choice) to isolate effects.

---

## Limitations & Future Work

- Stronger pruning and move ordering across all algorithms.
- Transposition tables and iterative deepening for time-bounded play.
- More robust team coordination (joint planning, communication models).
- Extended metrics (nodes expanded, branching factor, pruning ratios).
- Visualization overlays for reachable territory and cut areas.

---

## License

MIT

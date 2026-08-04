from pathlib import Path

from src.data_analysis import load_data, inspect_recipies

BASE_DIR = Path(__file__).resolve().parent
RECIPES_PATH = BASE_DIR / "data" /  "recipes.csv"
RATINGS_PATH = BASE_DIR / "data" /  "ratings.csv"

def main() -> None:
    recipies_df, ratings_df = load_data(
        RECIPES_PATH, 
        RATINGS_PATH
    )

    inspect_recipies(recipies_df)

if __name__ == "__main__":
    main()
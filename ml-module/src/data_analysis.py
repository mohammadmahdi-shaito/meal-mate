from pathlib import Path

import pandas as pd

def load_data( recipes_path: Path, 
               ratings_path: Path
)-> tuple[pd.DataFrame, pd.DataFrame]:
            recipies_df = pd.read_csv(recipes_path)
            ratings_df = pd.read_csv(ratings_path)

            return recipies_df, ratings_df

def inspect_recipies(df: pd.DataFrame) -> None:
        print ("\nFIRST FIVE RECIPES")
        print(df.head(5))


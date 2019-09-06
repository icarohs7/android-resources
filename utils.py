from typing import List, Tuple
from os import system
import re
import time


def get_gradle_modules() -> List[str]:
    with open("settings.gradle.kts") as f:
        lines = [l for l in f.readlines() if
                 re.search("^include", l) is not None]
        module_lines = [re.findall("\":[^\"]+\"", m) for m in lines]
        return [m for mods in module_lines for m in mods]


def execute_module_command(command: str, index: int, modules: List[str]) -> Tuple[int, float]:
    left: List[str] = modules[index:]
    total_number = len(modules)
    print()
    print(f"-----------------------------------------------------------------------------------------------------------")
    print(f"-----------------------------------------------------------------------------------------------------------")
    print(f"Executing command: {command}")
    print(f"module {index+1}/{total_number} - {modules[index]}")
    print(f"{len(left) - 1} modules left:")
    for module_left in left[1:]:
        print(module_left)
    print(f"-----------------------------------------------------------------------------------------------------------")
    print(f"-----------------------------------------------------------------------------------------------------------")
    start_time = time.time()
    command_result = system(command)
    end_time = time.time()
    return (command_result, end_time - start_time)


def print_execution_report(successes: List[str], total_time: float):
    print()
    print("Modules compiled successfully:")
    for module in successes:
        print(module)

    print()
    print("Total time of successful modules:")
    print(f"{len(successes)} modules compiled in {total_time:.1f}s")

    print()

import sys
import os
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


def build_module_command(gradle_exec: str, gradle_command_flags: str, module_name: str) -> str:
    assemble_cmd = "build"
    return f"{gradle_exec} {module_name}:{assemble_cmd} {gradle_command_flags}"


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


def assemble_debug_each(gradle_exec: str, gradle_command_flags: str):
    result = 0
    successes = []
    total_time = 0

    os.system("./gradlew clean")

    modules = [m for m in get_gradle_modules()]
    quote = "\""
    for (index, module) in enumerate(modules):
        command = build_module_command(gradle_exec, gradle_command_flags, module.replace(quote, ''))
        (result, cmd_time) = execute_module_command(command, index, modules)

        if result != 0:
            print_execution_report(successes, total_time)
            raise Exception(f"Module {module} failed to compile")

        total_time += cmd_time
        successes.append(f"{module} in {cmd_time:.1f}s")

    os.system("./gradlew jacocoRootReport")

    print_execution_report(successes, total_time)

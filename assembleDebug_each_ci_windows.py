from utils import execute_module_command, print_execution_report, get_gradle_modules
import sys

result = 0
successes = []
failed_module = ""
total_time = 0


def build_module_command(module_name: str) -> str:
    assemble_cmd = "assembleDebug"
    test_cmd = "testDebugUnitTest"
    flags = "-x lint -PrelativeResMod -Pci -Pcoverage --stacktrace"
    return f"gradle {module_name}:clean {module_name}:{assemble_cmd} {module_name}:{test_cmd} {flags}"


modules = [m for m in get_gradle_modules()]
quote = "\""
for (index, module) in enumerate(modules):
    command = build_module_command(module.replace(quote, ''))
    (result, cmd_time) = execute_module_command(command, index, modules)

    if result != 0:
        print_execution_report(successes, total_time)
        sys.exit(result)

    total_time += cmd_time
    successes.append(f"{module} in {cmd_time:.1f}s")

print_execution_report(successes, total_time)

import os
import datetime

modules = [f for f in os.listdir(".") if f.endswith("resources")]
with open("_settings.gradle.kts", "w") as file:
    dt = datetime.datetime.today()
    d = f"{dt.month:02}/{dt.day:02}/{dt.year:04}"
    t = f"{dt.hour:02}:{dt.minute:02}"
    file.write(
        f"// File created at {d} {t}\n"
        f"// {len(modules)} modules found\n\n"
    )
    for module in modules:
        file.write(f"include(\":{module}\")\n")
    file.write("\nsetupKotlinSettings()\n")

# Here you can create play commands that are specific to the module, and extend existing commands
import subprocess
import os

MODULE = 'play-shell'

# Commands that are specific to your module

COMMANDS = ['shell']

def execute(**kargs):
    command = kargs.get("command")
    app = kargs.get("app")
    args = kargs.get("args")
    env = kargs.get("env")
    play_env = kargs.get("env")

    if command == "shell":
        add_options = ['-Dapplication.path=%s' % (app.path), '-Dframework.path=%s' % (play_env['basedir']), '-Dplay.id=%s' % play_env['id'], '-Dplay.version=%s' % play_env['version']]
        java_cmd = [app.java_path()] + add_options + ["-Djava.awt.headless=true"] + ['-classpath', app.cp_args(), 'play.modules.shell.ShellRunner']
        subprocess.call(java_cmd, env=os.environ)


{ pkgs, ... }:

{
  # https://devenv.sh/basics/
  env.GREET = "devenv";
  env.DB_USER = "yuanw";

  # https://devenv.sh/packages/
  # packages = [ pkgs.git ];

  # https://devenv.sh/scripts/
  scripts.compile.exec = "gradle compileJava";
  scripts.format.exec = "gradle spotlessApply";
  scripts.run.exec = "gradle spotlessApply";

  enterShell = ''
  '';

  # https://devenv.sh/languages/
  languages.java.enable = true;
  languages.java.gradle.enable = true;

  # https://devenv.sh/pre-commit-hooks/
  # pre-commit.hooks.shellcheck.enable = true;
  services.postgres.enable = true;
  services.postgres.listen_addresses = "127.0.0.1";
  services.postgres.initialDatabases = [{
    name = "demo";
  }];
  # https://devenv.sh/processes/
  # processes.ping.exec = "ping example.com";

  # See full reference at https://devenv.sh/reference/options/
}

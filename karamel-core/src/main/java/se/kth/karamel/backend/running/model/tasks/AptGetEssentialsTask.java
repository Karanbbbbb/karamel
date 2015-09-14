/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.karamel.backend.running.model.tasks;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import se.kth.karamel.backend.ClusterService;
import se.kth.karamel.backend.converter.ShellCommandBuilder;
import se.kth.karamel.backend.machines.TaskSubmitter;
import se.kth.karamel.backend.running.model.MachineRuntime;
import se.kth.karamel.backend.stats.ClusterStats;
import se.kth.karamel.common.Settings;

/**
 *
 * @author kamal
 */
public class AptGetEssentialsTask extends Task {

  public AptGetEssentialsTask(MachineRuntime machine, ClusterStats clusterStats, TaskSubmitter submitter) {
    super("apt-get essentials", "apt-get essentials", machine, clusterStats, submitter);
  }

  @Override
  public List<ShellCommand> getCommands() throws IOException {
    if (commands == null) {
      commands = ShellCommandBuilder.fileScript2Commands(Settings.SCRIPT_PATH_APTGET_ESSENTIALS,
          "sudo_command", getSudoCommand(),
          "github_username", ClusterService.getInstance().getCommonContext().getGithubUsername());
    }
    return commands;
  }

  public static String makeUniqueId(String machineId) {
    return "apt-get essentials on " + machineId;
  }

  @Override
  public String uniqueId() {
    return makeUniqueId(super.getMachineId());
  }

  @Override
  public Set<String> dagDependencies() {
    return Collections.EMPTY_SET;
  }

}

import com.intellij.execution.filters.InputFilter;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.roots.ProjectRootManager;
import com.chakki_works.watchme.ProjectFileIterator;
import com.chakki_works.watchme.HandledMessage;
import com.chakki_works.watchme.*;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class ConsoleErrorInputFilter implements InputFilter {

    private final Project project;
    private ProjectFileContent projectContent;
    private ProjectRootManager projectRootManager;
    private String errorStack = "";

    public ConsoleErrorInputFilter(final Project project) {
        this.project = project;
        projectRootManager = ProjectRootManager.getInstance(project);
        errorStack = "";
        createFileCache();
    }

    @Nullable
    @Override
    public List<Pair<String, ConsoleViewContentType>> applyFilter(String text, ConsoleViewContentType outputType) {

        if (outputType.equals(ConsoleViewContentType.ERROR_OUTPUT)) {
            this.errorStack += text;
        }
        if (outputType.equals(ConsoleViewContentType.SYSTEM_OUTPUT) && text.trim().startsWith("Process finished with exit code")) {
            HandledMessage hm = HandledMessage.create(this.errorStack, this.projectContent);
            if(hm != null) {
                System.out.println(hm);
                for (SlackChannel slackChannel: SlackStorage.getInstance().channelsRegistry) {
                    SlackPost post = new SlackPost(slackChannel);
                    String message = hm.toString();
                    String detail = "Hironsan is in trouble with the following error.";
                    try {
                        post.pushMessage(message, detail);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            this.errorStack = "";
        }
        return Collections.singletonList(Pair.create(text, outputType));
    }

    private void createFileCache() {
        //VirtualFile[] projectFiles = projectRootManager.getContentRoots();
        final List<String> roots = new ArrayList<String>();
        VirtualFile[] vRoots;
        boolean isPython = projectRootManager.getProjectSdkName().toLowerCase().startsWith("python");

        if(isPython) {
            vRoots = projectRootManager.getContentRoots();
            String root = vRoots[0].getPath(); // only one root
            roots.add(root + "/.");
            roots.add(root + "/venv"); //heuristic!
            roots.add(root + "/env");
        }else{
            vRoots = projectRootManager.getContentSourceRoots();
            for (final VirtualFile root : vRoots) {
                roots.add(root.getPath());
            }
        }

        projectContent = new ProjectFileContent(roots, isPython);
        projectRootManager.getFileIndex().iterateContent(new ProjectFileIterator(projectContent));

    }

}

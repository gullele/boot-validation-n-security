package com.solutionladder.ethearts.persistence.entity;

/**
 * Anything related to the help that can be file, video, document..
 * 
 * @author Kaleb Woldearegay <kaleb@solutionladder.com>
 *
 */
public class HelpResource extends DatedEntity {
    private Help help;
    private Resource resource;
    private String comment;

    public Help getHelp() {
        return help;
    }

    public void setHelp(Help help) {
        this.help = help;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

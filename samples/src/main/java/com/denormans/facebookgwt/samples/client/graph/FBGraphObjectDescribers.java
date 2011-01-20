/*
 * Copyright (C) 2010 deNormans
 * http://www.denormans.com/
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of deNormans ("Confidential Information"). You 
 * shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with deNormans.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * DENORMANS OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.denormans.facebookgwt.samples.client.graph;

import com.denormans.facebookgwt.api.client.FBGWT;
import com.denormans.facebookgwt.api.client.common.js.FBJSObject;
import com.denormans.facebookgwt.api.client.graph.js.FBGraphObject;
import com.denormans.facebookgwt.api.client.graph.js.SimpleGraphObject;
import com.denormans.facebookgwt.api.client.graph.js.model.Account;
import com.denormans.facebookgwt.api.client.graph.js.model.Activity;
import com.denormans.facebookgwt.api.client.graph.js.model.Address;
import com.denormans.facebookgwt.api.client.graph.js.model.Application;
import com.denormans.facebookgwt.api.client.graph.js.model.Attachment;
import com.denormans.facebookgwt.api.client.graph.js.model.Book;
import com.denormans.facebookgwt.api.client.graph.js.model.CheckIn;
import com.denormans.facebookgwt.api.client.graph.js.model.Comment;
import com.denormans.facebookgwt.api.client.graph.js.model.Company;
import com.denormans.facebookgwt.api.client.graph.js.model.Education;
import com.denormans.facebookgwt.api.client.graph.js.model.EducationYear;
import com.denormans.facebookgwt.api.client.graph.js.model.Event;
import com.denormans.facebookgwt.api.client.graph.js.model.FriendList;
import com.denormans.facebookgwt.api.client.graph.js.model.Group;
import com.denormans.facebookgwt.api.client.graph.js.model.Image;
import com.denormans.facebookgwt.api.client.graph.js.model.Insights;
import com.denormans.facebookgwt.api.client.graph.js.model.Interest;
import com.denormans.facebookgwt.api.client.graph.js.model.Language;
import com.denormans.facebookgwt.api.client.graph.js.model.Link;
import com.denormans.facebookgwt.api.client.graph.js.model.Location;
import com.denormans.facebookgwt.api.client.graph.js.model.Message;
import com.denormans.facebookgwt.api.client.graph.js.model.MessageThread;
import com.denormans.facebookgwt.api.client.graph.js.model.Movie;
import com.denormans.facebookgwt.api.client.graph.js.model.Music;
import com.denormans.facebookgwt.api.client.graph.js.model.Note;
import com.denormans.facebookgwt.api.client.graph.js.model.Page;
import com.denormans.facebookgwt.api.client.graph.js.model.Photo;
import com.denormans.facebookgwt.api.client.graph.js.model.PhotoAlbum;
import com.denormans.facebookgwt.api.client.graph.js.model.PhotoTag;
import com.denormans.facebookgwt.api.client.graph.js.model.Place;
import com.denormans.facebookgwt.api.client.graph.js.model.Post;
import com.denormans.facebookgwt.api.client.graph.js.model.Postable;
import com.denormans.facebookgwt.api.client.graph.js.model.School;
import com.denormans.facebookgwt.api.client.graph.js.model.Share;
import com.denormans.facebookgwt.api.client.graph.js.model.StatusMessage;
import com.denormans.facebookgwt.api.client.graph.js.model.Subscription;
import com.denormans.facebookgwt.api.client.graph.js.model.TelevisionShow;
import com.denormans.facebookgwt.api.client.graph.js.model.User;
import com.denormans.facebookgwt.api.client.graph.js.model.Video;
import com.denormans.facebookgwt.api.client.graph.js.model.Work;
import com.denormans.facebookgwt.api.client.graph.js.model.WorkPosition;
import com.denormans.facebookgwt.api.client.graph.js.options.FeedPostOptions;
import com.denormans.facebookgwt.api.shared.graph.ObjectType;
import com.denormans.facebookgwt.samples.client.describe.AbstractObjectDescriber;
import com.denormans.facebookgwt.samples.client.describe.ObjectDescriber;
import com.denormans.facebookgwt.samples.client.describe.ObjectDescription;
import com.denormans.facebookgwt.samples.client.showcase.AbstractAction;
import com.denormans.facebookgwt.samples.client.showcase.AbstractParameterizedAction;

import com.google.gwt.i18n.client.TimeZone;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FBGraphObjectDescribers {
  private Map<ObjectType, ObjectDescriber<? extends FBGraphObject>> objectDescribers = new HashMap<ObjectType, ObjectDescriber<? extends FBGraphObject>>();

  private ObjectDescriber<Address> addressDescriber;
  private ObjectDescriber<Education> educationDescriber;
  private ObjectDescriber<Image> imageDescriber;
  private ObjectDescriber<PhotoTag> photoTagDescriber;
  private ObjectDescriber<Postable> postableDescriber;
  private ObjectDescriber<Work> workDescriber;

  public FBGraphObjectDescribers() {
    registerDescribers();
  }

  private void registerDescribers() {
    objectDescribers.put(ObjectType.Application, new ApplicationDescriber());
    objectDescribers.put(ObjectType.Attachment, new AttachmentDescriber());
    objectDescribers.put(ObjectType.CheckIn, new CheckInDescriber());
    objectDescribers.put(ObjectType.Comment, new CommentDescriber());
    objectDescribers.put(ObjectType.Event, new EventDescriber());
    objectDescribers.put(ObjectType.FriendList, new FriendListDescriber());
    objectDescribers.put(ObjectType.Group, new GroupDescriber());
    objectDescribers.put(ObjectType.Insights, new InsightsDescriber());
    objectDescribers.put(ObjectType.Link, new LinkDescriber());
    objectDescribers.put(ObjectType.Message, new MessageDescriber());
    objectDescribers.put(ObjectType.MessageThread, new MessageThreadDescriber());
    objectDescribers.put(ObjectType.Note, new NoteDescriber());
    objectDescribers.put(ObjectType.Page, new PageDescriber());
    objectDescribers.put(ObjectType.Photo, new PhotoDescriber());
    objectDescribers.put(ObjectType.PhotoAlbum, new PhotoAlbumDescriber());
    objectDescribers.put(ObjectType.Place, new PlaceDescriber());
    objectDescribers.put(ObjectType.Post, new PostDescriber());
    objectDescribers.put(ObjectType.Share, new ShareDescriber());
    objectDescribers.put(ObjectType.StatusMessage, new StatusMessageDescriber());
    objectDescribers.put(ObjectType.Subscription, new SubscriptionDescriber());
    objectDescribers.put(ObjectType.User, new UserDescriber());
    objectDescribers.put(ObjectType.Video, new VideoDescriber());

    objectDescribers.put(ObjectType.Account, new SimpleGraphObjectDescriber<Account>(ObjectType.Account));
    objectDescribers.put(ObjectType.Activity, new SimpleGraphObjectDescriber<Activity>(ObjectType.Activity));
    objectDescribers.put(ObjectType.Book, new SimpleGraphObjectDescriber<Book>(ObjectType.Book));
    objectDescribers.put(ObjectType.Company, new SimpleGraphObjectDescriber<Company>(ObjectType.Company));
    objectDescribers.put(ObjectType.EducationYear, new SimpleGraphObjectDescriber<EducationYear>(ObjectType.EducationYear));
    objectDescribers.put(ObjectType.Interest, new SimpleGraphObjectDescriber<Interest>(ObjectType.Interest));
    objectDescribers.put(ObjectType.Language, new SimpleGraphObjectDescriber<Location>(ObjectType.Language));
    objectDescribers.put(ObjectType.Location, new SimpleGraphObjectDescriber<Location>(ObjectType.Location));
    objectDescribers.put(ObjectType.Movie, new SimpleGraphObjectDescriber<Movie>(ObjectType.Movie));
    objectDescribers.put(ObjectType.Music, new SimpleGraphObjectDescriber<Music>(ObjectType.Music));
    objectDescribers.put(ObjectType.School, new SimpleGraphObjectDescriber<School>(ObjectType.School));
    objectDescribers.put(ObjectType.TelevisionShow, new SimpleGraphObjectDescriber<TelevisionShow>(ObjectType.TelevisionShow));
    objectDescribers.put(ObjectType.WorkPosition, new SimpleGraphObjectDescriber<WorkPosition>(ObjectType.WorkPosition));

    addressDescriber = new AddressDescriber();
    educationDescriber = new EducationDescriber();
    imageDescriber = new ImageDescriber();
    photoTagDescriber = new PhotoTagDescriber();
    postableDescriber = new PostableDescriber();
    workDescriber = new WorkDescriber();
  }

  private ObjectDescriber<Account> getAccountDescriber() {
    return getObjectDescriber(ObjectType.Account);
  }

  private ObjectDescriber<Activity> getActivityDescriber() {
    return getObjectDescriber(ObjectType.Activity);
  }

  private ObjectDescriber<Address> getAddressDescriber() {
    return addressDescriber;
  }

  public ObjectDescriber<Application> getApplicationDescriber() {
    return getObjectDescriber(ObjectType.Application);
  }

  private ObjectDescriber<Attachment> getAttachmentDescriber() {
    return getObjectDescriber(ObjectType.Attachment);
  }

  private ObjectDescriber<Book> getBookDescriber() {
    return getObjectDescriber(ObjectType.Book);
  }

  private ObjectDescriber<CheckIn> getCheckInDescriber() {
    return getObjectDescriber(ObjectType.CheckIn);
  }

  private ObjectDescriber<Comment> getCommentDescriber() {
    return getObjectDescriber(ObjectType.Comment);
  }

  private ObjectDescriber<Company> getCompanyDescriber() {
    return getObjectDescriber(ObjectType.Company);
  }

  private ObjectDescriber<Education> getEducationDescriber() {
    return educationDescriber;
  }

  private ObjectDescriber<EducationYear> getEducationYearDescriber() {
    return getObjectDescriber(ObjectType.EducationYear);
  }

  private ObjectDescriber<Event> getEventDescriber() {
    return getObjectDescriber(ObjectType.Event);
  }

  private ObjectDescriber<FriendList> getFriendListDescriber() {
    return getObjectDescriber(ObjectType.FriendList);
  }

  private ObjectDescriber<Group> getGroupDescriber() {
    return getObjectDescriber(ObjectType.Group);
  }

  private ObjectDescriber<Image> getImageDescriber() {
    return imageDescriber;
  }

  private ObjectDescriber<Insights> getInsightsDescriber() {
    return getObjectDescriber(ObjectType.Insights);
  }

  private ObjectDescriber<Interest> getInterestDescriber() {
    return getObjectDescriber(ObjectType.Interest);
  }

  private ObjectDescriber<Language> getLanguageDescriber() {
    return getObjectDescriber(ObjectType.Language);
  }

  private ObjectDescriber<Location> getLocationDescriber() {
    return getObjectDescriber(ObjectType.Location);
  }

  private ObjectDescriber<Link> getLinkDescriber() {
    return getObjectDescriber(ObjectType.Link);
  }

  private ObjectDescriber<Movie> getMovieDescriber() {
    return getObjectDescriber(ObjectType.Movie);
  }

  private ObjectDescriber<Music> getMusicDescriber() {
    return getObjectDescriber(ObjectType.Music);
  }

  private ObjectDescriber<Message> getMessageDescriber() {
    return getObjectDescriber(ObjectType.Message);
  }

  private ObjectDescriber<MessageThread> getMessageThreadDescriber() {
    return getObjectDescriber(ObjectType.MessageThread);
  }

  private ObjectDescriber<Note> getNoteDescriber() {
    return getObjectDescriber(ObjectType.Note);
  }

  private ObjectDescriber<Page> getPageDescriber() {
    return getObjectDescriber(ObjectType.Page);
  }

  private ObjectDescriber<Photo> getPhotoDescriber() {
    return getObjectDescriber(ObjectType.Photo);
  }

  private ObjectDescriber<PhotoAlbum> getPhotoAlbumDescriber() {
    return getObjectDescriber(ObjectType.PhotoAlbum);
  }

  private ObjectDescriber<PhotoTag> getPhotoTagDescriber() {
    return photoTagDescriber;
  }

  public ObjectDescriber<Post> getPostDescriber() {
    return getObjectDescriber(ObjectType.Post);
  }

  public ObjectDescriber<Place> getPlaceDescriber() {
    return getObjectDescriber(ObjectType.Place);
  }

  public ObjectDescriber<Postable> getPostableDescriber() {
    return postableDescriber;
  }

  private ObjectDescriber<School> getSchoolDescriber() {
    return getObjectDescriber(ObjectType.School);
  }

  private ObjectDescriber<Share> getShareDescriber() {
    return getObjectDescriber(ObjectType.Share);
  }

  private ObjectDescriber<StatusMessage> getStatusMessageDescriber() {
    return getObjectDescriber(ObjectType.StatusMessage);
  }

  private ObjectDescriber<Subscription> getSubscriptionDescriber() {
    return getObjectDescriber(ObjectType.Subscription);
  }

  private ObjectDescriber<TelevisionShow> getTelevisionShowDescriber() {
    return getObjectDescriber(ObjectType.TelevisionShow);
  }

  public ObjectDescriber<User> getUserDescriber() {
    return getObjectDescriber(ObjectType.User);
  }

  private ObjectDescriber<Video> getVideoDescriber() {
    return getObjectDescriber(ObjectType.Video);
  }

  private ObjectDescriber<Work> getWorkDescriber() {
    return workDescriber;
  }

  private ObjectDescriber<WorkPosition> getWorkPositionDescriber() {
    return getObjectDescriber(ObjectType.WorkPosition);
  }

  @SuppressWarnings({"unchecked"})
  public <T extends FBGraphObject> ObjectDescriber<T> getObjectDescriber(final ObjectType objectType) {
    return (ObjectDescriber<T>)objectDescribers.get(objectType);
  }

  private static class SimpleGraphObjectDescriber<T extends SimpleGraphObject> extends FBGraphObjectDescriber<T> {
    public SimpleGraphObjectDescriber(final ObjectType objectType) {
      super(objectType);
    }

    @Override
    protected ObjectDescription<T> describeObject(final T obj) {
      return super.describeObject(obj).addValue("Description", obj.getDescription()).addValue("Picture URL", obj.getPictureURL()).addValue("Page URL", obj.getPageURL()).addValue("Category", obj.getCategory()).
          addValue("Is Community Page?", obj.isCommunityPage()).addValue("# Likes", obj.getNumLikes());
    }
  }

  private abstract class PostableObjectDescriber<T extends Postable> extends AbstractGraphObjectDescriber<T> {
    private ObjectType objectType;

    public PostableObjectDescriber(final ObjectType objectType) {
      this.objectType = objectType;
    }

    @Override
    protected ObjectType getObjectType() {
      return objectType;
    }

    @Override
    protected ObjectDescription<T> describeObject(final T obj) {
      return super.describeObject(obj).addValue("From", getUserDescriber().describe(obj.getFrom())).addValue("Comments", getCommentDescriber().describeList(obj.getComments())).addValue("# Likes", obj.getNumLikes()).
          addValue("Likes", getUserDescriber().describeList(obj.getLikes())).
          addAction("Delete", new AbstractAction<T, Boolean>() {
            @Override
            public void execute(final T obj, final String param, final AsyncCallback<Boolean> callback) {
              FBGWT.Graph.Postable.delete(obj.getFullPostableID(), null, callback);
            }
          }).
          addAction("Comments", new AbstractAction<T, List<ObjectDescription<Comment>>>() {
            @Override
            public void execute(final T obj, final String param, final AsyncCallback<List<ObjectDescription<Comment>>> callback) {
              FBGWT.Graph.Postable.retrieveComments(obj.getID(), null, new ListTransformingCallback<Comment>(getCommentDescriber(), callback));
            }
          }).
          addAction("Post Comment", new AbstractParameterizedAction<T, ObjectDescription<Comment>>() {
            @Override
            public void execute(final T obj, final String param, final AsyncCallback<ObjectDescription<Comment>> callback) {
              FBGWT.Graph.Postable.postComment(obj.getID(), FeedPostOptions.createFeedPostOptions().setMessage(param), new ObjectTransformingCallback<Comment>(getCommentDescriber(), callback));
            }
          }).
          addAction("Likes", new AbstractAction<T, List<ObjectDescription<User>>>() {
            @Override
            public void execute(final T obj, final String param, final AsyncCallback<List<ObjectDescription<User>>> callback) {
              FBGWT.Graph.Postable.retrieveLikedByUsers(obj.getID(), null, new ListTransformingCallback<User>(getUserDescriber(), callback));
            }
          }).
          addAction("Like", new AbstractAction<T, Boolean>() {
            @Override
            public void execute(final T obj, final String param, final AsyncCallback<Boolean> callback) {
              FBGWT.Graph.Postable.like(obj.getFullPostableID(), callback);
            }
          }).
          addAction("Unlike", new AbstractAction<T, Boolean>() {
            @Override
            public void execute(final T obj, final String param, final AsyncCallback<Boolean> callback) {
              FBGWT.Graph.Postable.unlike(obj.getFullPostableID(), callback);
            }
          });
    }
  }

  private static abstract class FBGraphObjectDescriber<T extends FBGraphObject> extends AbstractGraphObjectDescriber<T> {
    private ObjectType objectType;

    public FBGraphObjectDescriber(final ObjectType objectType) {
      this.objectType = objectType;
    }

    @Override
    protected ObjectType getObjectType() {
      return objectType;
    }

    @Override
    protected ObjectDescription<T> describeObject(final T obj) {
      return super.describeObject(obj).addValue("Name", obj.getName());
    }
  }

  private static abstract class AbstractGraphObjectDescriber<T extends FBGraphObject> extends AbstractObjectDescriber<T> {
    @Override
    public String getObjectTypeName(final T obj) {
      return getObjectType().name();
    }

    @Override
    protected ObjectDescription<T> describeObject(final T obj) {
      return new GraphObjectDescription<T>(obj, this, getObjectType()).addValue("ID", obj.getID()).
          addAction("Get " + getObjectTypeName(obj), new AbstractAction<T, ObjectDescription<T>>() {
            @Override
            public void execute(final T obj, final String param, final AsyncCallback<ObjectDescription<T>> callback) {
              retrieveItem(obj.getID(), new ObjectTransformingCallback<T>(AbstractGraphObjectDescriber.this, callback));
            }
          });
    }

    protected abstract ObjectType getObjectType();

    protected void retrieveItem(final String itemID, final AsyncCallback<T> callback) {
      FBGWT.Graph.retrieveItem(itemID, null, callback);
    }

    /**
     * HACK: This is needed because of issue http://bugs.developers.facebook.net/show_bug.cgi?id=10714
     *
     * <tt>/likes/</tt> and <tt>delete</tt> need an ID that looks like USERID_ITEMID
     *
     * @param obj The object
     * @param user The object's user
     *
     * @return The full ID for use with like and delete
     */
    protected String getFullID(final T obj, final User user) {
      String id = obj.getID();
      if (user != null && !id.contains(user.getID())) {
        id = user.getID() + "_" + id;
      }
      return id;
    }
  }

  private static class ObjectTransformingCallback<T extends FBJSObject> implements AsyncCallback<T> {
    private final ObjectDescriber<T> describer;
    private final AsyncCallback<ObjectDescription<T>> callback;

    public ObjectTransformingCallback(final ObjectDescriber<T> describer, final AsyncCallback<ObjectDescription<T>> callback) {
      this.describer = describer;
      this.callback = callback;
    }

    @Override
    public void onFailure(final Throwable caught) {
      callback.onFailure(caught);
    }

    @Override
    public void onSuccess(final T result) {
      callback.onSuccess(describer.describe(result));
    }
  }

  private static class ListTransformingCallback<T extends FBJSObject> implements AsyncCallback<List<T>> {
    private final ObjectDescriber<T> describer;
    private final AsyncCallback<List<ObjectDescription<T>>> callback;

    private ListTransformingCallback(final ObjectDescriber<T> describer, final AsyncCallback<List<ObjectDescription<T>>> callback) {
      this.describer = describer;
      this.callback = callback;
    }

    @Override
    public void onFailure(final Throwable caught) {
      callback.onFailure(caught);
    }

    @Override
    public void onSuccess(final List<T> result) {
      callback.onSuccess(describer.describeList(result));
    }
  }

  private class ApplicationDescriber extends FBGraphObjectDescriber<Application> {
    public ApplicationDescriber() {
      super(ObjectType.Application);
    }

    @Override
    protected ObjectDescription<Application> describeObject(final Application obj) {
      // todo: describe application actions
      return super.describeObject(obj).addValue("Description", obj.getDescription()).addValue("Category", obj.getCategory()).addValue("Page URL", obj.getPageURL());
    }
  }

  private class AttachmentDescriber extends FBGraphObjectDescriber<Attachment> {
    public AttachmentDescriber() {
      super(ObjectType.Attachment);
    }

    @Override
    protected ObjectDescription<Attachment> describeObject(final Attachment obj) {
      // todo: describe attachment
      return super.describeObject(obj);
    }
  }

  private class CheckInDescriber extends PostableObjectDescriber<CheckIn> {
    public CheckInDescriber() {
      super(ObjectType.CheckIn);
    }

    @Override
    protected ObjectDescription<CheckIn> describeObject(final CheckIn obj) {
      // todo: describe check-in
      return super.describeObject(obj).addValue("Message", obj.getMessage()).addValue("Place", getPlaceDescriber().describe(obj.getPlace())).addValue("User Tags", getUserDescriber().describeList(obj.getUserTags())).addValue("Application", getApplicationDescriber().describe(obj.getApplication())).
          addValue("Created Time", obj.getCreatedTime());
    }
  }

  private class CommentDescriber extends FBGraphObjectDescriber<Comment> {
    public CommentDescriber() {
      super(ObjectType.Comment);
    }

    @Override
    protected ObjectDescription<Comment> describeObject(final Comment obj) {
      return super.describeObject(obj).addValue("Message", obj.getMessage()).addValue("From", getUserDescriber().describe(obj.getFrom())).addValue("# Likes", obj.getNumLikes()).addValue("Created Time", obj.getCreatedTime()).
          addAction("Delete", new AbstractAction<Comment, Boolean>() {
            @Override
            public void execute(final Comment obj, final String param, final AsyncCallback<Boolean> callback) {
              FBGWT.Graph.Postable.delete(obj.getID(), null, callback);
            }
          }).
          addAction("Likes", new AbstractAction<Comment, List<ObjectDescription<User>>>() {
            @Override
            public void execute(final Comment obj, final String param, final AsyncCallback<List<ObjectDescription<User>>> callback) {
              FBGWT.Graph.Comment.retrieveLikedByUsers(obj.getID(), null, new ListTransformingCallback<User>(getUserDescriber(), callback));
            }
          }).
          addAction("Like", new AbstractAction<Comment, Boolean>() {
            @Override
            public void execute(final Comment obj, final String param, final AsyncCallback<Boolean> callback) {
              FBGWT.Graph.Comment.like(obj.getFullPostableID(), callback);
            }
          }).
          addAction("Unlike", new AbstractAction<Comment, Boolean>() {
            @Override
            public void execute(final Comment obj, final String param, final AsyncCallback<Boolean> callback) {
              FBGWT.Graph.Comment.unlike(obj.getFullPostableID(), callback);
            }
          });
    }
  }

  private class EventDescriber extends FBGraphObjectDescriber<Event> {
    public EventDescriber() {
      super(ObjectType.Event);
    }

    @Override
    protected ObjectDescription<Event> describeObject(final Event obj) {
      // todo: describe event
      return super.describeObject(obj).addValue("Owner", getUserDescriber().describe(obj.getOwner())).addValue("Description", obj.getDescription()).addValue("Start Time", obj.getStartTime()).addValue("End Time", obj.getEndTime()).
          addValue("Location", obj.getLocation()).addValue("Venue", getAddressDescriber().describe(obj.getVenue())).addValue("Privacy", obj.getPrivacy()).addValue("RSVP Status", obj.getRSVPStatus()).
          addValue("Updated Time", obj.getUpdatedTime());
    }
  }

  private class FriendListDescriber extends FBGraphObjectDescriber<FriendList> {
    public FriendListDescriber() {
      super(ObjectType.FriendList);
    }

    @Override
    protected ObjectDescription<FriendList> describeObject(final FriendList obj) {
      return super.describeObject(obj).
          addAction("Delete", new AbstractAction<FriendList, Boolean>() {
            @Override
            public void execute(final FriendList obj, final String param, final AsyncCallback<Boolean> callback) {
              FBGWT.Graph.FriendList.delete(obj.getID(), null, callback);
            }
          }).
          addAction("Members", new AbstractAction<FriendList, List<ObjectDescription<User>>>() {
            @Override
            public void execute(final FriendList obj, final String param, final AsyncCallback<List<ObjectDescription<User>>> callback) {
              FBGWT.Graph.FriendList.retrieveMembers(obj.getID(), null, new ListTransformingCallback<User>(getUserDescriber(), callback));
            }
          }).
          addAction("Add Member", new AbstractParameterizedAction<FriendList, Boolean>() {
            @Override
            public void execute(final FriendList obj, final String param, final AsyncCallback<Boolean> callback) {
              FBGWT.Graph.FriendList.addMember(obj.getID(), param, callback);
            }
          }).
          addAction("Remove Member", new AbstractParameterizedAction<FriendList, Boolean>() {
            @Override
            public void execute(final FriendList obj, final String param, final AsyncCallback<Boolean> callback) {
              FBGWT.Graph.FriendList.removeMember(obj.getID(), param, callback);
            }
          });
    }
  }

  private class GroupDescriber extends FBGraphObjectDescriber<Group> {
    public GroupDescriber() {
      super(ObjectType.Group);
    }

    @Override
    protected ObjectDescription<Group> describeObject(final Group obj) {
      // todo: describe group actions
      return super.describeObject(obj).addValue("Version", obj.getVersion()).addValue("Description", obj.getDescription()).addValue("Icon URL", obj.getIconURL()).addValue("Page URL", obj.getPageURL()).
          addValue("Owner", getUserDescriber().describe(obj.getOwner())).addValue("Privacy", obj.getPrivacy()).addValue("Updated Time", obj.getUpdatedTime());
    }
  }

  private class InsightsDescriber extends FBGraphObjectDescriber<Insights> {
    public InsightsDescriber() {
      super(ObjectType.Insights);
    }

    @Override
    protected ObjectDescription<Insights> describeObject(final Insights obj) {
      // todo: describe insights
      return super.describeObject(obj);
    }
  }

  private class LinkDescriber extends FBGraphObjectDescriber<Link> {
    public LinkDescriber() {
      super(ObjectType.Link);
    }

    @Override
    protected ObjectDescription<Link> describeObject(final Link obj) {
      // todo: describe link
      return super.describeObject(obj);
    }
  }

  private class MessageDescriber extends FBGraphObjectDescriber<Message> {
    public MessageDescriber() {
      super(ObjectType.Message);
    }

    @Override
    protected ObjectDescription<Message> describeObject(final Message obj) {
      // todo: describe message
      return super.describeObject(obj);
    }
  }

  private class MessageThreadDescriber extends FBGraphObjectDescriber<MessageThread> {
    public MessageThreadDescriber() {
      super(ObjectType.MessageThread);
    }

    @Override
    protected ObjectDescription<MessageThread> describeObject(final MessageThread obj) {
      // todo: describe note
      return super.describeObject(obj);
    }
  }

  private class NoteDescriber extends FBGraphObjectDescriber<Note> {
    public NoteDescriber() {
      super(ObjectType.Note);
    }

    @Override
    protected ObjectDescription<Note> describeObject(final Note obj) {
      // todo: describe note
      return super.describeObject(obj);
    }
  }

  private class PageDescriber extends SimpleGraphObjectDescriber<Page> {
    public PageDescriber() {
      super(ObjectType.Page);
    }

    @Override
    protected ObjectDescription<Page> describeObject(final Page obj) {
      // todo: describe page
      return super.describeObject(obj);
    }
  }

  private class PhotoDescriber extends PostableObjectDescriber<Photo> {
    public PhotoDescriber() {
      super(ObjectType.Photo);
    }

    @Override
    protected ObjectDescription<Photo> describeObject(final Photo obj) {
      // todo: describe photo actions
      return super.describeObject(obj).addValue("Name", obj.getName()).addValue("Page URL", obj.getPageURL()).addValue("Icon URL", obj.getIconURL()).addValue("Tags", getPhotoTagDescriber().describeList(obj.getTags())).
          addValue("Full Size Image", getImageDescriber().describe(obj.getFullSizeImage())).addValue("Images", getImageDescriber().describeList(obj.getImages())).addValue("Album Position", obj.getAlbumPosition()).
          addValue("Created Time", obj.getCreatedTime()).addValue("Updated Time", obj.getUpdatedTime());
    }
  }

  private class PhotoAlbumDescriber extends FBGraphObjectDescriber<PhotoAlbum> {
    public PhotoAlbumDescriber() {
      super(ObjectType.PhotoAlbum);
    }

    @Override
    protected ObjectDescription<PhotoAlbum> describeObject(final PhotoAlbum obj) {
      return super.describeObject(obj).addValue("From", getUserDescriber().describe(obj.getFrom())).addValue("Description", obj.getDescription()).addValue("Location", obj.getLocation()).addValue("Page URL", obj.getPageURL()).
          addValue("# Photos", obj.getNumPhotos()).addValue("Type", obj.getType()).addValue("Privacy", obj.getPrivacy()).addValue("Created Time", obj.getCreatedTime()).addValue("Updated Time", obj.getUpdatedTime()).
          addAction("Photos", new AbstractAction<PhotoAlbum, List<ObjectDescription<Photo>>>() {
            @Override
            public void execute(final PhotoAlbum obj, final String param, final AsyncCallback<List<ObjectDescription<Photo>>> callback) {
              FBGWT.Graph.PhotoAlbum.retrievePhotos(obj.getID(), null, new ListTransformingCallback<Photo>(getPhotoDescriber(), callback));
            }
          }).
          addAction("Comments", new AbstractAction<PhotoAlbum, List<ObjectDescription<Comment>>>() {
            @Override
            public void execute(final PhotoAlbum obj, final String param, final AsyncCallback<List<ObjectDescription<Comment>>> callback) {
              FBGWT.Graph.PhotoAlbum.retrieveComments(obj.getID(), null, new ListTransformingCallback<Comment>(getCommentDescriber(), callback));
            }
          });
    }
  }

  private class PlaceDescriber extends SimpleGraphObjectDescriber<Place> {
    public PlaceDescriber() {
      super(ObjectType.Place);
    }

    @Override
    protected ObjectDescription<Place> describeObject(final Place obj) {
      // todo: describe place
      return super.describeObject(obj).addValue("Location", getAddressDescriber().describe(obj.getLocation()));
    }
  }

  private class PostDescriber extends PostableObjectDescriber<Post> {
    public PostDescriber() {
      super(ObjectType.Post);
    }

    @Override
    protected ObjectDescription<Post> describeObject(final Post obj) {
      // todo: describe post
      return super.describeObject(obj).
          addAction("Delete", new AbstractAction<Post, Boolean>() {
            @Override
            public void execute(final Post obj, final String param, final AsyncCallback<Boolean> callback) {
              FBGWT.Graph.deleteItem(obj.getID(), null, callback);
            }
          });
    }
  }

  private class PostableDescriber extends PostableObjectDescriber<Postable> {
    public PostableDescriber() {
      super(null);
    }

    @Override
    public String getObjectTypeName(final Postable obj) {
      // todo: get the exact type
      return "Postable";
    }
  }

  private class ShareDescriber extends FBGraphObjectDescriber<Share> {
    public ShareDescriber() {
      super(ObjectType.Share);
    }

    @Override
    protected ObjectDescription<Share> describeObject(final Share obj) {
      // todo: describe share
      return super.describeObject(obj);
    }
  }

  private class StatusMessageDescriber extends PostableObjectDescriber<StatusMessage> {
    public StatusMessageDescriber() {
      super(ObjectType.StatusMessage);
    }

    @Override
    protected ObjectDescription<StatusMessage> describeObject(final StatusMessage obj) {
      return super.describeObject(obj).addValue("Message", obj.getMessage()).addValue("Updated Time", obj.getUpdatedTime());

    }
  }

  private class SubscriptionDescriber extends FBGraphObjectDescriber<Subscription> {
    public SubscriptionDescriber() {
      super(ObjectType.Subscription);
    }

    @Override
    protected ObjectDescription<Subscription> describeObject(final Subscription obj) {
      // todo: describe subscription
      return super.describeObject(obj);
    }
  }

  private class UserDescriber extends FBGraphObjectDescriber<User> {
    public UserDescriber() {
      super(ObjectType.User);
    }

    @Override
    protected ObjectDescription<User> describeObject(final User obj) {
      // todo: describe user content actions
      final ObjectDescriber<User> describer = this;
      TimeZone timeZone = obj.getTimeZone();
      return super.describeObject(obj).addValue("Name", obj.getName()).addValue("First Name", obj.getFirstName()).addValue("Last Name", obj.getLastName()).addValue("Link", obj.getLink()).addValue("About", obj.getAbout()).
          addValue("Birthday", obj.getBirthday()). addValue("Work", getWorkDescriber().describeList(obj.getWork())).addValue("Education", getEducationDescriber().describeList(obj.getEducation())).addValue("Email", obj.getEmail()).
          addValue("Website", obj.getWebsite()). addValue("Location", getLocationDescriber().describe(obj.getLocation())).addValue("Biography", obj.getBiography()).addValue("Quotes", obj.getQuotes()).addValue("Gender", obj.getGender()).
          addValue("Interested in", obj.getInterestedIn()).addValue("Seeking", obj.getSeeking()).addValue("Religion", obj.getReligion()).addValue("Political Affiliation", obj.getPoliticalAffiliation()).
          addValue("Verified", obj.isVerified()).addValue("Significant Other", describe(obj.getSignificantOther())).addValue("Time Zone", timeZone != null ? timeZone.getID() : null).addValue("Third-Party ID", obj.getThirdPartyID()).
          addValue("Locale", obj.getLocale()).addValue("Address", getAddressDescriber().describe(obj.getAddress())).addValue("Mobile Phone", obj.getMobilePhone()).addValue("Relationship Status", obj.getRelationshipStatus()).
          addValue("Languages", getLanguageDescriber().describeList(obj.getLanguages())).addValue("Updated Time", obj.getUpdatedTime()).
          addAction("Home Feed", new AbstractAction<User, List<ObjectDescription<Post>>>() {
            @Override
            public void execute(final User obj, final String param, final AsyncCallback<List<ObjectDescription<Post>>> callback) {
              FBGWT.Graph.User.retrieveHomeFeed(obj.getID(), null, new ListTransformingCallback<Post>(getPostDescriber(), callback));
            }
          }).
              addAction("Wall Feed", new AbstractAction<User, List<ObjectDescription<Post>>>() {
                @Override
                public void execute(final User obj, final String param, final AsyncCallback<List<ObjectDescription<Post>>> callback) {
                  FBGWT.Graph.User.retrieveWallFeed(obj.getID(), null, new ListTransformingCallback<Post>(getPostDescriber(), callback));
                }
              }).
              addAction("Post to Wall", new AbstractParameterizedAction<User, ObjectDescription<Post>>() {
                @Override
                public void execute(final User obj, final String param, final AsyncCallback<ObjectDescription<Post>> callback) {
                  FeedPostOptions postOptions = FeedPostOptions.createFeedPostOptions().setMessage(param);
                  FBGWT.Graph.User.postToWall(obj.getID(), postOptions, new ObjectTransformingCallback<Post>(getPostDescriber(), callback));
                }
              }).
              addAction("Tagged In", new AbstractAction<User, List<ObjectDescription<Postable>>>() {
                @Override
                public void execute(final User obj, final String param, final AsyncCallback<List<ObjectDescription<Postable>>> callback) {
                  FBGWT.Graph.User.retrieveTaggedIn(obj.getID(), null, new ListTransformingCallback<Postable>(getPostableDescriber(), callback));
                }
              }).
              addAction("Posts", new AbstractAction<User, List<ObjectDescription<Post>>>() {
                @Override
                public void execute(final User obj, final String param, final AsyncCallback<List<ObjectDescription<Post>>> callback) {
                  FBGWT.Graph.User.retrievePosts(obj.getID(), null, new ListTransformingCallback<Post>(getPostDescriber(), callback));
                }
              }).
              addAction("Friends", new AbstractAction<User, List<ObjectDescription<User>>>() {
                @Override
                public void execute(final User obj, final String param, final AsyncCallback<List<ObjectDescription<User>>> callback) {
                  FBGWT.Graph.User.retrieveFriends(obj.getID(), null, new ListTransformingCallback<User>(describer, callback));
                }
              }).
              addAction("Activities", new AbstractAction<User, List<ObjectDescription<Activity>>>() {
                @Override
                public void execute(final User obj, final String param, final AsyncCallback<List<ObjectDescription<Activity>>> callback) {
                  FBGWT.Graph.User.retrieveActivities(obj.getID(), null, new ListTransformingCallback<Activity>(getActivityDescriber(), callback));
                }
              }).
              addAction("Interests", new AbstractAction<User, List<ObjectDescription<Interest>>>() {
                @Override
                public void execute(final User obj, final String param, final AsyncCallback<List<ObjectDescription<Interest>>> callback) {
                  FBGWT.Graph.User.retrieveInterests(obj.getID(), null, new ListTransformingCallback<Interest>(getInterestDescriber(), callback));
                }
              }).
              addAction("Music", new AbstractAction<User, List<ObjectDescription<Music>>>() {
                @Override
                public void execute(final User obj, final String param, final AsyncCallback<List<ObjectDescription<Music>>> callback) {
                  FBGWT.Graph.User.retrieveMusic(obj.getID(), null, new ListTransformingCallback<Music>(getMusicDescriber(), callback));
                }
              }).
              addAction("Books", new AbstractAction<User, List<ObjectDescription<Book>>>() {
                @Override
                public void execute(final User obj, final String param, final AsyncCallback<List<ObjectDescription<Book>>> callback) {
                  FBGWT.Graph.User.retrieveBooks(obj.getID(), null, new ListTransformingCallback<Book>(getBookDescriber(), callback));
                }
              }).
              addAction("Movies", new AbstractAction<User, List<ObjectDescription<Movie>>>() {
                @Override
                public void execute(final User obj, final String param, final AsyncCallback<List<ObjectDescription<Movie>>> callback) {
                  FBGWT.Graph.User.retrieveMovies(obj.getID(), null, new ListTransformingCallback<Movie>(getMovieDescriber(), callback));
                }
              }).
              addAction("Television Shows", new AbstractAction<User, List<ObjectDescription<TelevisionShow>>>() {
                @Override
                public void execute(final User obj, final String param, final AsyncCallback<List<ObjectDescription<TelevisionShow>>> callback) {
                  FBGWT.Graph.User.retrieveTelevisionShows(obj.getID(), null, new ListTransformingCallback<TelevisionShow>(getTelevisionShowDescriber(), callback));
                }
              }).
              addAction("Liked Pages", new AbstractAction<User, List<ObjectDescription<Page>>>() {
                @Override
                public void execute(final User obj, final String param, final AsyncCallback<List<ObjectDescription<Page>>> callback) {
                  FBGWT.Graph.User.retrieveLikedPages(obj.getID(), null, new ListTransformingCallback<Page>(getPageDescriber(), callback));
                }
              }).
              addAction("Photos", new AbstractAction<User, List<ObjectDescription<Photo>>>() {
                @Override
                public void execute(final User obj, final String param, final AsyncCallback<List<ObjectDescription<Photo>>> callback) {
                  FBGWT.Graph.User.retrievePhotos(obj.getID(), null, new ListTransformingCallback<Photo>(getPhotoDescriber(), callback));
                }
              }).
              addAction("Photo Albums", new AbstractAction<User, List<ObjectDescription<PhotoAlbum>>>() {
                @Override
                public void execute(final User obj, final String param, final AsyncCallback<List<ObjectDescription<PhotoAlbum>>> callback) {
                  FBGWT.Graph.User.retrievePhotoAlbums(obj.getID(), null, new ListTransformingCallback<PhotoAlbum>(getPhotoAlbumDescriber(), callback));
                }
              }).
              addAction("Videos", new AbstractAction<User, List<ObjectDescription<Video>>>() {
                @Override
                public void execute(final User obj, final String param, final AsyncCallback<List<ObjectDescription<Video>>> callback) {
                  FBGWT.Graph.User.retrieveVideos(obj.getID(), null, new ListTransformingCallback<Video>(getVideoDescriber(), callback));
                }
              }).
              addAction("Groups", new AbstractAction<User, List<ObjectDescription<Group>>>() {
                @Override
                public void execute(final User obj, final String param, final AsyncCallback<List<ObjectDescription<Group>>> callback) {
                  FBGWT.Graph.User.retrieveGroups(obj.getID(), null, new ListTransformingCallback<Group>(getGroupDescriber(), callback));
                }
              }).
              addAction("Status Messages", new AbstractAction<User, List<ObjectDescription<StatusMessage>>>() {
                @Override
                public void execute(final User obj, final String param, final AsyncCallback<List<ObjectDescription<StatusMessage>>> callback) {
                  FBGWT.Graph.User.retrieveStatusMessages(obj.getID(), null, new ListTransformingCallback<StatusMessage>(getStatusMessageDescriber(), callback));
                }
              }).
              addAction("Links", new AbstractAction<User, List<ObjectDescription<Link>>>() {
                @Override
                public void execute(final User obj, final String param, final AsyncCallback<List<ObjectDescription<Link>>> callback) {
                  FBGWT.Graph.User.retrieveLinks(obj.getID(), null, new ListTransformingCallback<Link>(getLinkDescriber(), callback));
                }
              }).
              addAction("Notes", new AbstractAction<User, List<ObjectDescription<Note>>>() {
                @Override
                public void execute(final User obj, final String param, final AsyncCallback<List<ObjectDescription<Note>>> callback) {
                  FBGWT.Graph.User.retrieveNotes(obj.getID(), null, new ListTransformingCallback<Note>(getNoteDescriber(), callback));
                }
              }).
              addAction("Events", new AbstractAction<User, List<ObjectDescription<Event>>>() {
                @Override
                public void execute(final User obj, final String param, final AsyncCallback<List<ObjectDescription<Event>>> callback) {
                  FBGWT.Graph.User.retrieveEvents(obj.getID(), null, new ListTransformingCallback<Event>(getEventDescriber(), callback));
                }
              }).
              addAction("Inbox", new AbstractAction<User, List<ObjectDescription<MessageThread>>>() {
                @Override
                public void execute(final User obj, final String param, final AsyncCallback<List<ObjectDescription<MessageThread>>> callback) {
                  FBGWT.Graph.User.retrieveInbox(obj.getID(), null, new ListTransformingCallback<MessageThread>(getMessageThreadDescriber(), callback));
                }
              }).
              addAction("Outbox", new AbstractAction<User, List<ObjectDescription<Note>>>() {
                @Override
                public void execute(final User obj, final String param, final AsyncCallback<List<ObjectDescription<Note>>> callback) {
                  FBGWT.Graph.User.retrieveOutbox(obj.getID(), null, new ListTransformingCallback<Note>(getNoteDescriber(), callback));
                }
              }).
              addAction("Updates", new AbstractAction<User, List<ObjectDescription<Note>>>() {
                @Override
                public void execute(final User obj, final String param, final AsyncCallback<List<ObjectDescription<Note>>> callback) {
                  FBGWT.Graph.User.retrieveUpdates(obj.getID(), null, new ListTransformingCallback<Note>(getNoteDescriber(), callback));
                }
              }).
              addAction("Accounts", new AbstractAction<User, List<ObjectDescription<Account>>>() {
                @Override
                public void execute(final User obj, final String param, final AsyncCallback<List<ObjectDescription<Account>>> callback) {
                  FBGWT.Graph.User.retrieveAccounts(obj.getID(), null, new ListTransformingCallback<Account>(getAccountDescriber(), callback));
                }
              }).
              addAction("Check-ins", new AbstractAction<User, List<ObjectDescription<CheckIn>>>() {
                @Override
                public void execute(final User obj, final String param, final AsyncCallback<List<ObjectDescription<CheckIn>>> callback) {
                  FBGWT.Graph.User.retrieveCheckIns(obj.getID(), null, new ListTransformingCallback<CheckIn>(getCheckInDescriber(), callback));
                }
              }).
              addAction("Friend Lists", new AbstractAction<User, List<ObjectDescription<FriendList>>>() {
                @Override
                public void execute(final User obj, final String param, final AsyncCallback<List<ObjectDescription<FriendList>>> callback) {
                  FBGWT.Graph.User.retrieveFriendLists(obj.getID(), null, new ListTransformingCallback<FriendList>(getFriendListDescriber(), callback));
                }
              }).
              addAction("Create Friend List", new AbstractParameterizedAction<User, ObjectDescription<FriendList>>() {
                @Override
                public void execute(final User obj, final String param, final AsyncCallback<ObjectDescription<FriendList>> callback) {
                  FBGWT.Graph.User.createFriendList(obj.getID(), param, new ObjectTransformingCallback<FriendList>(getFriendListDescriber(), callback));
                }
              });
    }
  }

  private class VideoDescriber extends PostableObjectDescriber<Video> {
    public VideoDescriber() {
      super(ObjectType.Video);
    }

    @Override
    protected ObjectDescription<Video> describeObject(final Video obj) {
      return super.describeObject(obj).addValue("Tags", getUserDescriber().describeList(obj.getTags())).addValue("Embed HTML URL", obj.getEmbedHTMLURL()).addValue("Icon URL", obj.getIconURL()).
          addValue("Source URL", obj.getSourceURL()).addValue("Created Time", obj.getCreatedTime()).addValue("Updated Time", obj.getUpdatedTime());
    }
  }

  // non graph objects
  private class EducationDescriber extends AbstractObjectDescriber<Education> {
    @Override
    public String getObjectTypeName(final Education obj) {
      return "Education";
    }
    @Override
    public ObjectDescription<Education> describeObject(final Education obj) {
      return new ObjectDescription<Education>(obj, this).addValue("School", getSchoolDescriber().describe(obj.getSchool())).addValue("Year", getEducationYearDescriber().describe(obj.getYear())).addValue("Type", obj.getType());
    }
  }

  private class ImageDescriber extends AbstractObjectDescriber<Image> {
    @Override
    public String getObjectTypeName(final Image obj) {
      return "Image";
    }
    @Override
    public ObjectDescription<Image> describeObject(final Image obj) {
      return new ObjectDescription<Image>(obj, this).addValue("Photo URL", obj.getImageURL()).addValue("Height", obj.getHeight()).addValue("Width", obj.getWidth());
    }
  }

  private class PhotoTagDescriber extends AbstractObjectDescriber<PhotoTag> {
    @Override
    public String getObjectTypeName(final PhotoTag obj) {
      return "Photo Tag";
    }
    @Override
    public ObjectDescription<PhotoTag> describeObject(final PhotoTag obj) {
      return new ObjectDescription<PhotoTag>(obj, this).addValue("Tagged User", getUserDescriber().describe(obj.getTaggedUser())).addValue("X", obj.getX()).addValue("Y", obj.getY());
    }

  }

  private class WorkDescriber extends AbstractObjectDescriber<Work> {
    @Override
    public String getObjectTypeName(final Work obj) {
      return "Work";
    }

    @Override
    public ObjectDescription<Work> describeObject(final Work obj) {
      return new ObjectDescription<Work>(obj, this).addValue("Employer", getCompanyDescriber().describe(obj.getEmployer())).addValue("Location", getLocationDescriber().describe(obj.getLocation())).
          addValue("Position", getWorkPositionDescriber().describe(obj.getPosition())).addValue("Start Date", obj.getStartDate()).addValue("End Date", obj.getEndDate());
    }
  }

  private class AddressDescriber extends AbstractObjectDescriber<Address> {
    @Override
    public String getObjectTypeName(final Address obj) {
      return "Address";
    }

    @Override
    protected ObjectDescription<Address> describeObject(final Address obj) {
      return new ObjectDescription<Address>(obj, this).addValue("Street", obj.getStreet()).addValue("City", obj.getCity()).addValue("State", obj.getState()).addValue("Country", obj.getCountry()).addValue("Postal Code", obj.getPostalCode()).
          addValue("Has Latitude/Longitude?", obj.hasLatitudeAndLongitude()).addValue("Latitude", obj.getLatitude()).addValue("Longitude", obj.getLongitude());
    }
  }
}
